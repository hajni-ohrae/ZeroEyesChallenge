package biz.ohrae.challenge_screen.ui.mychallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.ui.register.ChallengeRegisterNavScreen
import biz.ohrae.challenge_screen.ui.register.RegisterAuthScreen

class MyChallengeActivity : AppCompatActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            modifier = Modifier.fillMaxSize()
        ) {
            Navigation()
        }
    }

    @Composable
    private fun Navigation() {

        NavHost(
            navController = navController,
            startDestination = MyChallengeNavScreen.MyChallenge.route
        ) {
            composable(MyChallengeNavScreen.MyChallenge.route) {
                MyChallengeScreen()
            }
        }
    }
}


sealed class MyChallengeNavScreen(val route: String) {
    object MyChallenge : MyChallengeNavScreen("MyChallenge")
    object MyReward : MyChallengeNavScreen("MyReward")
}