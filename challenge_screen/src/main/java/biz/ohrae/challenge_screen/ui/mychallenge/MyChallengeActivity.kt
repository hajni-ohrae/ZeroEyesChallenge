package biz.ohrae.challenge_screen.ui.mychallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultWhite

class MyChallengeActivity : AppCompatActivity() {
//    private lateinit var challengeMainViewModel: ChallengeMainViewModel
    private lateinit var navController: NavHostController
    private lateinit var myChallengeClickListener: MyChallengeClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        challengeMainViewModel = ViewModelProvider(this)[ChallengeMainViewModel::class.java]

        initClickListener()
        setContent {
            ChallengeInTheme {
                BuildContent()
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
//        val state by challengeMainViewModel.userChallengeListState.observeAsState()

        NavHost(
            navController = navController,
            startDestination = MyChallengeNavScreen.MyChallenge.route
        ) {
            composable(MyChallengeNavScreen.MyChallenge.route) {
                MyChallengeScreen(clickListener = myChallengeClickListener)
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
                PaymentDetailListScreen()
            }
            composable(MyChallengeNavScreen.SavedChallenge.route) {
                SavedChallengeScreen()
            }
            composable(MyChallengeNavScreen.RedCard.route) {
                RedCardScreen()
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
}