package biz.ohrae.challenge_screen.ui.register

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeRegisterViewModel

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeRegisterViewModel::class.java]

        init()
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
            BackButton(onBack = {onBack()})
            Column(modifier = Modifier.padding(24.dp,0.dp)) {
                Navigation()

            }
        }
    }
    @Composable
    private fun Navigation() {
        NavHost(
            navController = navController,
            startDestination = ChallengeRegisterNavScreen.ChallengeGoals.route
        ) {
            composable(ChallengeRegisterNavScreen.ChallengeGoals.route) {
                ChallengeGoals()
            }
            composable(ChallengeRegisterNavScreen.ChallengeOpen.route){
                ChallengeOpenScreen()
            }

            composable(ChallengeRegisterNavScreen.ChallengerRecruitment.route){
                ChallengerRecruitment()
            }

            composable(ChallengeRegisterNavScreen.RegisterAuth.route){
                RegisterAuthScreen()
            }


        }
    }
    private fun init() {
    }
    private fun onBack() {
        finish()
    }
}

sealed class ChallengeRegisterNavScreen(val route: String) {
    object ChallengeGoals : ChallengeRegisterNavScreen("ChallengeGoals")
    object ChallengeOpen : ChallengeRegisterNavScreen("ChallengeOpen")
    object ChallengerRecruitment : ChallengeRegisterNavScreen("ChallengerRecruitment")
    object RegisterAuth : ChallengeRegisterNavScreen("RegisterAuth")
}