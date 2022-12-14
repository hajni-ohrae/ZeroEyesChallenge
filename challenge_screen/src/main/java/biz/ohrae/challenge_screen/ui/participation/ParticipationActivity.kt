package biz.ohrae.challenge_screen.ui.participation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailViewModel
import biz.ohrae.challenge_screen.ui.dialog.CustomDialog
import biz.ohrae.challenge_screen.ui.dialog.CustomDialogListener
import biz.ohrae.challenge_screen.ui.dialog.LoadingDialog
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeActivity
import biz.ohrae.challenge_screen.ui.payment.ChallengePaymentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParticipationActivity : BaseActivity() {
    private var challengeId: String? = null
    private lateinit var detailViewModel: ChallengeDetailViewModel
    private lateinit var viewModel: ParticipationViewModel
    private lateinit var navController: NavHostController
    private lateinit var clickListener: ParticipationClickListener
    private lateinit var paymentLauncher: ActivityResultLauncher<Intent>

    private var isCancelChallenge = false
    private var isFreeChallenge = false

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

        paymentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    it.data?.let { intent ->
                        val isSuccess = intent.getBooleanExtra("isSuccess", false)
                        if (isSuccess) {
                            val cardName = intent.getStringExtra("cardName")
                            val amount = intent.getStringExtra("amount")
                            val rewardsAmount = intent.getStringExtra("rewardsAmount")

                            viewModel.setPaymentInfo(
                                cardName.toString(),
                                amount.toString(),
                                rewardsAmount.toString()
                            )
                            navController.navigate(ChallengeParticipationNavScreen.ParticipationFinish.route)
                        } else {
                            val code = intent.getStringExtra("code")
                            val message = intent.getStringExtra("message")
                            showSnackBar(code, message)
                        }
                    }
                }
            }

        initClickListeners()
        observeViewModels()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @Composable
    private fun BuildContent() {
        val title = if (isCancelChallenge) {
            "챌린지 참여 취소"
        } else {
            "챌린지 참여"
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultWhite)
        ) {
            BackButton(onBack = { onBack() }, title)
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
                val paidInfo by viewModel.paidInfo.observeAsState()

                if (challengeData != null && participationResult != null) {
                    ParticipationFinishScreen(
                        challengeData = challengeData!!,
                        participationResult = participationResult,
                        paidInfo = paidInfo,
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
                val cancelResult by viewModel.cancelResult.observeAsState()

                if (challengeData != null && cancelResult != null) {
                    ParticipationCancelResultScreen(
                        challengeData = challengeData!!,
                        cancelResult = cancelResult!!,
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
                val minDeposit = detailViewModel.challengeData.value?.min_deposit_amount ?: 1000
                if (depositAmount < minDeposit) {
                    showSnackBar("참여금은 ${Utils.numberToString(minDeposit.toString())}원 이상이어야 합니다.")
                    return
                }
                detailViewModel.challengeData.value?.let {
                    viewModel.isLoading(true)
                    viewModel.registerChallenge(
                        challengeData = it,
                        paidAmount,
                        rewardAmount,
                        depositAmount
                    )
                }
            }

            // 챌린지 참여 취소
            override fun onClickCancelParticipation(isFree: Boolean) {
                isFreeChallenge = isFree
                if (!isClickable()) {
                    return
                }
                detailViewModel.challengeData.value?.let {
                    viewModel.cancelChallenge(it)
                }
                if (isFree) {
                    showCancelDialog()
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
                val intent =
                    Intent(this@ParticipationActivity, MyChallengeActivity::class.java)
                intent.putExtra("challengeId", challengeId)
                startActivity(intent)
                finish()
            }

            override fun onClickHome() {
                setResult(RESULT_OK)
                finish()
            }

            override fun onClickSetAlarm() {
                val intent = Intent()
                intent.putExtra("challengeAlarm", true)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun observeViewModels() {
        viewModel.participationResult.observe(this) { result ->
            viewModel.isLoading(false)
            result?.let {
                val minDepositAmount = detailViewModel.challengeData.value?.min_deposit_amount ?: 0
                val paidAmount = viewModel.participationResult.value?.paid_amount ?: 0

                if (minDepositAmount > 0 && paidAmount > 0) {
                    val userId = prefs.getUserData()?.id
                    val intent =
                        Intent(this@ParticipationActivity, ChallengePaymentActivity::class.java)
                    intent.putExtra("challengeId", challengeId)
                    intent.putExtra("userId", userId)
                    intent.putExtra("userInChallengeId", it.user_in_challenge_id)
                    intent.putExtra("paidAmount", it.paid_amount)
                    intent.putExtra("rewardsAmount", it.rewards_amount)

                    paymentLauncher.launch(intent)
                } else {
                    init()
                    viewModel.setPaymentInfo("리워즈", "0", "0")
                    navController.navigate(ChallengeParticipationNavScreen.ParticipationFinish.route)
                }
            } ?: run {
                val code = viewModel.errorData.value?.code
                val message = viewModel.errorData.value?.message
                showSnackBar(code, message)
            }
        }

        viewModel.cancelResult.observe(this) { result ->
            viewModel.isLoading(false)
            if (result != null && !isFreeChallenge) {
                navController.navigate(ChallengeParticipationNavScreen.ParticipationCancelResult.route)
            }
        }

        viewModel.errorData.observe(this) {
            it?.let {
                if (it.code == "4352" || it.code == "6102") {
                    showDialog(it.code.toString())
                } else {
                    showSnackBar(it.code, it.message)
                }
            }
        }
    }

    override fun onBack() {
        when (navController.currentBackStackEntry?.destination?.route) {
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

    private fun showDialog(code: String) {
        val content = if (code == "4352") {
            "비슷한 유형의 챌린지를 이미 진행중입니다."
        } else {
            "해당 매장 이용중이 아닙니다."
        }

        val dialog =
            CustomDialog(positiveBtnName = "확인", content = content)
        dialog.isCancelable = false
        dialog.setListener(object : CustomDialogListener {
            override fun clickPositive() {
                dialog.dismiss()
            }

            override fun clickNegative() {
                dialog.dismiss()
            }
        })
        dialog.show(supportFragmentManager, "showDialog")
    }

    private fun showCancelDialog() {
        val content = "참여 취소가 완료되었습니다."

        val dialog =
            CustomDialog(positiveBtnName = "홈으로", content = content)
        dialog.isCancelable = false
        dialog.setListener(object : CustomDialogListener {
            override fun clickPositive() {
                setResult(RESULT_OK)
                finish()
                dialog.dismiss()
            }

            override fun clickNegative() {
                dialog.dismiss()
            }
        })
        dialog.show(supportFragmentManager, "showDialog")
    }
}

sealed class ChallengeParticipationNavScreen(val route: String) {
    object Participation : ChallengeParticipationNavScreen("Participation")
    object ParticipationFinish : ChallengeParticipationNavScreen("ParticipationFinish")
    object ParticipationPayment : ChallengeParticipationNavScreen("ParticipationPayment")
    object ParticipationCancelRequest :
        ChallengeParticipationNavScreen("ParticipationCancelRequest")

    object ParticipationCancelResult : ChallengeParticipationNavScreen("ParticipationCancelResult")
}