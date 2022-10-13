package biz.ohrae.challenge_screen.ui.participation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailViewModel
import biz.ohrae.challenge_screen.ui.payment.ChallengePaymentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParticipationActivity : BaseActivity() {
    private var challengeId: String? = null
    private lateinit var detailViewModel: ChallengeDetailViewModel
    private lateinit var viewModel: ParticipationViewModel
    private lateinit var navController: NavHostController
    private lateinit var clickListener: ParticipationClickListener

    private var isCancelChallenge = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }

        detailViewModel = ViewModelProvider(this)[ChallengeDetailViewModel::class.java]
        viewModel = ViewModelProvider(this)[ParticipationViewModel::class.java]

        challengeId = intent.getStringExtra("challengeId")
        isCancelChallenge = intent.getBooleanExtra("isCancel", false)

        initClickListeners()
        observeViewModels()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @Composable
    private fun BuildContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultWhite)
        ) {
            BackButton(onBack = { onBack() },"챌린지 참여")
            Column(modifier = Modifier) {
                Navigation()
            }
        }
    }

    @Composable
    private fun Navigation() {
        navController = rememberNavController()
        val startDestination = if (isCancelChallenge) {
            ChallengeParticipationNavScreen.ParticipationCancelRequest.route
        } else {
            ChallengeParticipationNavScreen.Participation.route
        }

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(ChallengeParticipationNavScreen.Participation.route) {
                val challengeData by detailViewModel.challengeData.observeAsState()
                if (challengeData != null) {
                    ParticipationScreen(
                        challengeData = challengeData!!,
                        clickListener = clickListener
                    )
                }
            }
            composable(ChallengeParticipationNavScreen.ParticipationPayment.route) {
                ParticipationPaymentScreen()
            }
            composable(ChallengeParticipationNavScreen.ParticipationFinish.route) {
                val challengeData by detailViewModel.challengeData.observeAsState()
                if (challengeData != null) {
                    ParticipationFinishScreen(
                        challengeData = challengeData!!,
                        clickListener = clickListener
                    )
                }
            }
            composable(ChallengeParticipationNavScreen.ParticipationCancelRequest.route) {
                val challengeData by detailViewModel.challengeData.observeAsState()
                if (challengeData != null) {
                    ParticipationCancelRequestScreen(
                        challengeData = challengeData!!,
                        clickListener = clickListener
                    )
                }
            }
            composable(ChallengeParticipationNavScreen.ParticipationCancelResult.route) {
                val challengeData by detailViewModel.challengeData.observeAsState()
                if (challengeData != null) {
                    ParticipationCancelResultScreen(
                        challengeData = challengeData!!,
                        clickListener = clickListener
                    )
                }
            }
        }
    }

    private fun init() {
        detailViewModel.getChallenge(challengeId.toString())
    }

    override fun initClickListeners() {
        clickListener = object : ParticipationClickListener {
            override fun onClickPayment(paidAmount: Int, rewardAmount: Int, depositAmount: Int) {
                navController.navigate(ChallengeParticipationNavScreen.ParticipationPayment.route)
                val intent = Intent(this@ParticipationActivity, ChallengePaymentActivity::class.java)
                startActivity(intent)
//                detailViewModel.challengeData.value?.let {
//                    viewModel.registerChallenge(challengeData = it, paidAmount, rewardAmount, depositAmount)
//                }
            }

            // 챌린지 참여 취소
            override fun onClickCancelParticipation() {
                detailViewModel.challengeData.value?.let {
                    viewModel.cancelChallenge(it)
                }
            }

            // 챌린지 취소 후 확인
            override fun onClickCancelResult() {
                setResult(RESULT_OK)
                finish()
            }

            // 챌린지 취소 후 결제상세 보기
            override fun onClickPaymentDetail() {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    override fun observeViewModels() {
        viewModel.registerResult.observe(this) { result ->
            result.data?.let {
                setResult(RESULT_OK)
                finish()
            } ?: run {
                val message = "code : ${result.errorCode}, message : ${result.errorMessage}"
                showSnackBar(message)
            }
        }

        viewModel.cancelResult.observe(this) { result ->
            result.data?.let {
                navController.navigate(ChallengeParticipationNavScreen.ParticipationCancelResult.route)
            } ?: run {
                val message = "code : ${result.errorCode}, message : ${result.errorMessage}"
                showSnackBar(message)
            }
        }
    }

    override fun onBack() {
        finish()
    }
}

sealed class ChallengeParticipationNavScreen(val route: String) {
    object Participation : ChallengeParticipationNavScreen("Participation")
    object ParticipationFinish : ChallengeParticipationNavScreen("ParticipationFinish")
    object ParticipationPayment : ChallengeParticipationNavScreen("ParticipationPayment")
    object ParticipationCancelRequest : ChallengeParticipationNavScreen("ParticipationCancelRequest")
    object ParticipationCancelResult : ChallengeParticipationNavScreen("ParticipationCancelResult")
}