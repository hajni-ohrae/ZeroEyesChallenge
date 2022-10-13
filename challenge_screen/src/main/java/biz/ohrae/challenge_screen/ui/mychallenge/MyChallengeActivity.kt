package biz.ohrae.challenge_screen.ui.mychallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.ui.main.ChallengeMainViewModel
import biz.ohrae.challenge_screen.ui.register.ChallengeRegisterNavScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyChallengeActivity : AppCompatActivity() {

    companion object {
        const val REWARD: String = "reward"
        const val RED_CARD: String = "redCard"
    }

    private lateinit var challengeMainViewModel: ChallengeMainViewModel
    private lateinit var myChallengeViewModel: MyChallengeViewModel
    private lateinit var navController: NavHostController
    private lateinit var myChallengeClickListener: MyChallengeClickListener
    private lateinit var prefs: SharedPreference

    private var policyScreenType: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        challengeMainViewModel = ViewModelProvider(this)[ChallengeMainViewModel::class.java]
        myChallengeViewModel = ViewModelProvider(this)[MyChallengeViewModel::class.java]
        init()
        initClickListener()
        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    private fun init() {
        challengeMainViewModel.getUserChallengeList()
        challengeMainViewModel.getChallengeList("","","","","1","")
        myChallengeViewModel.getRedCardList()
        myChallengeViewModel.getPaymentHistory()
        myChallengeViewModel.getUserData()
    }
    @Composable
    private fun BuildContent() {
        navController = rememberNavController()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultWhite)
        ) {
            BackButton(onBack = { onBack() })
            Column() {
                Navigation()
            }
        }
    }

    private fun onBack() {
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
        val paymentHistoryState by myChallengeViewModel.paymentHistoryState.observeAsState()
        val saveChallengeList by challengeMainViewModel.mainScreenState.observeAsState()

        NavHost(
            navController = navController,
            startDestination = MyChallengeNavScreen.MyChallenge.route
        ) {
            composable(MyChallengeNavScreen.MyChallenge.route) {
                MyChallengeScreen(user = userData,clickListener = myChallengeClickListener, userChallengeListState = state)
            }
            composable(MyChallengeNavScreen.MyReward.route) {
                MyRewardScreen(clickListener = myChallengeClickListener)
            }
            composable(MyChallengeNavScreen.Withdraw.route) {
                WithdrawScreen(clickListener = myChallengeClickListener)
            }
            composable(MyChallengeNavScreen.PhoneAuth.route) {
                PhoneAuthScreenWebView()
            }
            composable(MyChallengeNavScreen.MyPaymentDetail.route) {
                PaymentDetailListScreen(paymentHistoryState)
            }
            composable(MyChallengeNavScreen.SavedChallenge.route) {
                SavedChallengeScreen(saveChallengeList)
            }
            composable(MyChallengeNavScreen.RedCard.route) {
                RedCardScreen(clickListener = myChallengeClickListener,redCardListState)
            }
            composable(MyChallengeNavScreen.Policy.route) {
                PolicyScreen(policyScreenType)
            }
        }
    }

    private fun initClickListener() {
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
                navController.navigate(MyChallengeNavScreen.Withdraw.route)
            }

            override fun onClickApplyWithdrawDetail() {
                navController.navigate(MyChallengeNavScreen.PhoneAuth.route)
            }

            override fun onClickPolicy(screen: String) {
                policyScreenType = screen
                navController.navigate(MyChallengeNavScreen.Policy.route)
            }

            override fun onClickChallengeAuthItem(item:String) {
                TODO("Not yet implemented")
            }
        }
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
}