package biz.ohrae.challenge_screen.ui.participation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
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
import biz.ohrae.challenge_screen.ui.dialog.LoadingDialog
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
                val isLoading by viewModel.isLoading.observeAsState(false)
                if (isLoading) {
                    Dialog(onDismissRequest = { /*TODO*/ }) {
                        LoadingDialog()
                    }
                }
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
                BackHandler(true) {
                    onBack()
                }
                val challengeData by detailViewModel.challengeData.observeAsState()
                val participationResult by viewModel.participationResult.observeAsState()

                if (challengeData != null && participationResult != null) {
                    ParticipationFinishScreen(
                        challengeData = challengeData!!,
                        participationResult = participationResult,
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
                BackHandler(true) {
                    onBack()
                }
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
                detailViewModel.challengeData.value?.let {
                    if (it.min_deposit_amount > 0) {
                        val userId = prefs.getUserData()?.id
                        val intent = Intent(this@ParticipationActivity, ChallengePaymentActivity::class.java)
                        intent.putExtra("challengeId", challengeId)
                        intent.putExtra("userId", userId)
                        startActivity(intent)
                    } else {
                        viewModel.isLoading(true)
                        viewModel.registerChallenge(challengeData = it, paidAmount, rewardAmount, depositAmount)
                    }
                }
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

            override fun onClickHome() {
                setResult(RESULT_OK)
                finish()
            }

            override fun onClickSetAlarm() {
                showSnackBar("준비중입니다.")
            }
        }
    }

    override fun observeViewModels() {
        viewModel.participationResult.observe(this) { result ->
            viewModel.isLoading(false)

            result?.let {
                init()
                navController.navigate(ChallengeParticipationNavScreen.ParticipationFinish.route)
            } ?: run {
                val code = viewModel.errorData.value?.code
                val message = viewModel.errorData.value?.message
                showSnackBar(code, message)
            }
        }

        viewModel.registerResult.observe(this) { result ->
            viewModel.isLoading(false)
            result.data?.let {
                navController.navigate(ChallengeParticipationNavScreen.ParticipationFinish.route)
            } ?: run {
                val message = "code : ${result.errorCode}, message : ${result.errorMessage}"
                showSnackBar(message)
            }
        }

        viewModel.cancelResult.observe(this) { result ->
            viewModel.isLoading(false)
            result.data?.let {
                navController.navigate(ChallengeParticipationNavScreen.ParticipationCancelResult.route)
            } ?: run {
                val message = "code : ${result.errorCode}, message : ${result.errorMessage}"
                showSnackBar(message)
            }
        }
    }

    override fun onBack() {
        when(navController.currentBackStackEntry?.destination?.route) {
            ChallengeParticipationNavScreen.Participation.route,
            ChallengeParticipationNavScreen.ParticipationCancelResult.route,
            ChallengeParticipationNavScreen.ParticipationFinish.route -> {
                finish()
            }
            else -> {
                navController.popBackStack()
            }
        }
    }
}

sealed class ChallengeParticipationNavScreen(val route: String) {
    object Participation : ChallengeParticipationNavScreen("Participation")
    object ParticipationFinish : ChallengeParticipationNavScreen("ParticipationFinish")
    object ParticipationPayment : ChallengeParticipationNavScreen("ParticipationPayment")
    object ParticipationCancelRequest : ChallengeParticipationNavScreen("ParticipationCancelRequest")
    object ParticipationCancelResult : ChallengeParticipationNavScreen("ParticipationCancelResult")
}