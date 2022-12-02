package biz.ohrae.challenge_screen.ui.mychallenge

import AccountAuthScreen
import android.content.Intent
import android.os.Bundle
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
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailActivity
import biz.ohrae.challenge_screen.ui.dialog.LoadingDialog
import biz.ohrae.challenge_screen.ui.main.ChallengeMainViewModel
import biz.ohrae.challenge_screen.ui.niceid.NiceIdActivity
import biz.ohrae.challenge_screen.ui.policy.PolicyActivity
import biz.ohrae.challenge_screen.ui.profile.ChallengeProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyChallengeActivity : BaseActivity() {
    companion object {
        const val REWARD: String = "reward"
        const val RED_CARD: String = "redCard"
    }

    private lateinit var challengeMainViewModel: ChallengeMainViewModel
    private lateinit var myChallengeViewModel: MyChallengeViewModel
    private lateinit var navController: NavHostController
    private lateinit var myChallengeClickListener: MyChallengeClickListener
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private var policyScreenType: String = ""
    private var headerTitle: String = ""
    private var filterType: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        challengeMainViewModel = ViewModelProvider(this)[ChallengeMainViewModel::class.java]
        myChallengeViewModel = ViewModelProvider(this)[MyChallengeViewModel::class.java]

        setContent {
            val isLoading by myChallengeViewModel.isLoading.observeAsState(false)
            if (isLoading) {
                Dialog(onDismissRequest = { /*TODO*/ }) {
                    LoadingDialog()
                }
            }
            ChallengeInTheme {
                BuildContent()
            }
        }

        challengeMainViewModel.selectFilter("all")
        initClickListeners()
        observeViewModels()
        initLauncher()
    }

    private fun init() {
        challengeMainViewModel.getUserChallengeList("", isInit = true)
        challengeMainViewModel.getChallengeList("", "", "", "", "1", "", isInit = true)
        myChallengeViewModel.getRedCardList(isInit = true)
        myChallengeViewModel.getPaymentHistory(isInit = true)
        myChallengeViewModel.getUserData()
        myChallengeViewModel.getRewardHistory("", isInit = true)
    }

    private fun initLauncher() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.let { intent ->
                    val done = intent.getBooleanExtra("done", false)
                    if (done) {
                        if (myChallengeViewModel.userData.value != null) {
                            authOrWithdraw(myChallengeViewModel.userData.value!!)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BuildContent() {
        navController = rememberNavController()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultWhite)
        ) {
            BackButton(onBack = { onBack() }, title = headerTitle)
            Column() {
                Navigation()
            }
        }
        myChallengeViewModel.selectRewardFilter("all")
    }

    override fun onResume() {
        super.onResume()

        init()
    }

    override fun onBack() {
        if (navController.currentBackStackEntry?.destination?.route == MyChallengeNavScreen.MyChallenge.route) {
            finish()
        }
        navController.popBackStack()
    }

    @Composable
    private fun Navigation() {
        val state by challengeMainViewModel.userChallengeListState.observeAsState()
        val userData by myChallengeViewModel.userData.observeAsState()
        val redCardListState by myChallengeViewModel.redCardListState.observeAsState()
        val rewardList by myChallengeViewModel.rewardList.observeAsState()
        val paymentHistoryState by myChallengeViewModel.paymentHistoryState.observeAsState()
        val saveChallengeList by challengeMainViewModel.mainScreenState.observeAsState()
        val isRefreshing by challengeMainViewModel.isRefreshing.observeAsState(false)
        val filterState by challengeMainViewModel.filterState.observeAsState()
        val rewardFilter by myChallengeViewModel.rewardFilter.observeAsState()

        NavHost(
            navController = navController,
            startDestination = MyChallengeNavScreen.MyChallenge.route
        ) {
            composable(MyChallengeNavScreen.MyChallenge.route) {
                MyChallengeScreen(
                    user = userData,
                    clickListener = myChallengeClickListener,
                    userChallengeListState = state,
                    filterState = filterState!!,
                    isRefreshing = isRefreshing,
                    onBottomReached = {
                        onBottomReached()
                    },
                    onRefresh = {
                        challengeMainViewModel.isRefreshing(true)
                        init()
                    }
                )
            }
            composable(MyChallengeNavScreen.MyReward.route) {
                MyRewardScreen(
                    user = userData,
                    clickListener = myChallengeClickListener,
                    rewardList = rewardList,
                    rewardFilter = rewardFilter!!,
                    onBottomReached = { myChallengeViewModel.getRewardHistory("", isInit = true) }
                )
            }
            composable(MyChallengeNavScreen.Withdraw.route) {
                WithdrawScreen(
                    rewardsAmount = userData?.rewards_amount ?: 0,
                    clickListener = myChallengeClickListener
                )
            }
            composable(MyChallengeNavScreen.PhoneAuth.route) {
                PhoneAuthScreenWebView(
                    userId = userData?.id.toString()
                )
            }
            composable(MyChallengeNavScreen.AccountAuth.route) {
                val accountScreenState by myChallengeViewModel.accountScreenState.observeAsState()
                val bankList by myChallengeViewModel.bankList.observeAsState()

                if (accountScreenState != null && bankList != null) {
                    AccountAuthScreen(
                        accountScreenState = accountScreenState!!,
                        bankList = bankList!!,
                        clickListener = myChallengeClickListener
                    )
                }
            }
            composable(MyChallengeNavScreen.MyPaymentDetail.route) {
                PaymentDetailListScreen(
                    paymentHistoryList = paymentHistoryState,
                    clickListener = myChallengeClickListener,
                    onBottomReached = {
                        myChallengeViewModel.getPaymentHistory(isInit = true)
                    },)
            }
            composable(MyChallengeNavScreen.SavedChallenge.route) {
                SavedChallengeScreen(
                    saveChallengeList,
                    myChallengeClickListener,
                    isRefreshing = isRefreshing,
                    onBottomReached = {
                        challengeMainViewModel.getChallengeList(
                            "",
                            "",
                            "",
                            "",
                            "1",
                            "",
                            isInit = true
                        )
                    },
                    onRefresh = {
                        challengeMainViewModel.isRefreshing(true)
                        init()
                    })
            }
            composable(MyChallengeNavScreen.RedCard.route) {
                RedCardScreen(
                    clickListener = myChallengeClickListener,
                    redCardListState,
                    onBottomReached = {
                        myChallengeViewModel.getRedCardList(isInit = true)
                    })
            }
            composable(MyChallengeNavScreen.Policy.route) {
                PolicyScreen(policyScreenType)
            }
        }
    }

    override fun initClickListeners() {
        myChallengeClickListener = object : MyChallengeClickListener {
            override fun onClickReward() {
                navController.navigate(MyChallengeNavScreen.MyReward.route)
            }

            override fun onClickPaymentDetail() {
                navController.navigate(MyChallengeNavScreen.MyPaymentDetail.route)
            }

            override fun onClickSavedChallenge() {
                navController.navigate(MyChallengeNavScreen.SavedChallenge.route)
            }

            override fun onClickRedCard() {
                navController.navigate(MyChallengeNavScreen.RedCard.route)
            }

            override fun onClickApplyWithdraw() {
                val userData = myChallengeViewModel.userData.value
                if (userData?.is_identified == 1) {
                    authOrWithdraw(userData)
                } else {
                    val intent = Intent(this@MyChallengeActivity, NiceIdActivity::class.java)
                    intent.putExtra("userId", myChallengeViewModel.userData.value?.id)
                    launcher.launch(intent)
                }
            }

            override fun onClickApplyWithdrawDetail(amountText: String) {
                if (!isClickable()) {
                    return
                }

                val amount = amountText.replace(",", "").toInt()
                if (amount >= 5000) {
                    myChallengeViewModel.isLoading(true)
                    myChallengeViewModel.transferRewards(amount)
                } else {
                    showSnackBar("5000원 이상 출금 가능합니다.")
                }
            }

            override fun onClickPolicy(screen: String) {
                policyScreenType = screen
                navController.navigate(MyChallengeNavScreen.Policy.route)
            }

            override fun onClickPolicyRefund() {
                val intent = Intent(this@MyChallengeActivity, PolicyActivity::class.java)
                intent.putExtra("policyType", "Refund")
                startActivity(intent)
            }

            override fun onClickChallengeAuthItem(challengeId: String, type: Int) {
                goDetail(challengeId, isPhoto = type)
            }

            override fun onClickMyChallengeCard(id: String) {
                goDetail(id)
            }

            override fun onClickChallengeItem(id: String) {
                val intent = Intent(this@MyChallengeActivity, ChallengeDetailActivity::class.java)
                intent.putExtra("challengeId", id)
                startActivity(intent)
            }

            override fun onClickFilterType(type: String) {
                filterType = type
                if (!type.isNullOrEmpty()) {
                    if (type == "all") filterType = ""
                    challengeMainViewModel.selectUserFilter(type)
                    challengeMainViewModel.getUserChallengeList(filterType, isInit = true)
                }
            }

            override fun onClickRewardFilterType(type: String) {
                var filterType = type
                if (!type.isNullOrEmpty()) {
                    if (type == "all") filterType = ""
                    myChallengeViewModel.selectRewardFilter(type)
                    myChallengeViewModel.getRewardHistory(filterType)
                }
            }

            override fun onClickProfile() {
                val intent = Intent(this@MyChallengeActivity, ChallengeProfileActivity::class.java)
                startActivity(intent)
            }

            override fun onClickAccountAuth(isDone: Boolean) {
                myChallengeViewModel.authAccountNumber()
            }

            override fun onClickRegisterAccountNumber(bankCode: String, accountNumber: String) {
                myChallengeViewModel.registerAccountNumber(bankCode, accountNumber)
            }
        }
    }

    override fun observeViewModels() {
        myChallengeViewModel.accountRegistered.observe(this) {
            it?.let {
                if (it) {
                    navController.navigate(MyChallengeNavScreen.Withdraw.route)
                }
            }
        }

        myChallengeViewModel.transferRewards.observe(this) {
            it?.let {
                if (it) {
                    navController.navigate(MyChallengeNavScreen.MyChallenge.route) {
                        popUpTo(MyChallengeNavScreen.MyChallenge.route) {
                            inclusive = true
                        }
                    }
                    init()
                    showSnackBar("출금신청이 완료되었습니다.")
                }
            }
            myChallengeViewModel.isLoading(false)
        }

        myChallengeViewModel.errorData.observe(this) {
            it?.let {
                showSnackBar(it.code, it.message)
            }
        }
    }

    private fun goDetail(id: String, isPhoto: Int = 0) {
        val intent = Intent(this, ChallengeDetailActivity::class.java)
        intent.putExtra("challengeId", id)
        intent.putExtra("isPhoto", isPhoto)
        startActivity(intent)
    }

    private fun authOrWithdraw(userData: User) {
        if (userData.has_bank_account == 1) {
            navController.navigate(MyChallengeNavScreen.Withdraw.route)
        } else {
            myChallengeViewModel.retrieveBankCodes()
            navController.navigate(MyChallengeNavScreen.AccountAuth.route)
        }
    }

    private fun onBottomReached() {
        challengeMainViewModel.getUserChallengeList(filterType, isInit = true)
    }
}


sealed class MyChallengeNavScreen(val route: String) {
    object MyChallenge : MyChallengeNavScreen("MyChallenge")
    object MyReward : MyChallengeNavScreen("MyReward")
    object Withdraw : MyChallengeNavScreen("Withdraw")
    object MyPaymentDetail : MyChallengeNavScreen("MyPaymentDetail")
    object RedCard : MyChallengeNavScreen("RedCard")
    object SavedChallenge : MyChallengeNavScreen("SavedChallenge")
    object PhoneAuth : MyChallengeNavScreen("PhoneAuth")
    object Policy : MyChallengeNavScreen("Policy")
    object AccountAuth : MyChallengeNavScreen("AccountAuth")
}