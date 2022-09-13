package biz.ohrae.challenge_screen.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_screen.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeRegisterViewModel

    private lateinit var navController: NavHostController
    private lateinit var registerClickListener: RegisterClickListener
    private lateinit var challengeData: ChallengeData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeRegisterViewModel::class.java]

        init()
        initClickListener()
        observeViewModel()
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
            Column(modifier = Modifier) {
                Navigation()

            }
        }
    }

    @Composable
    private fun Navigation() {
        val challengeDataState by viewModel.challengeData.observeAsState()

        NavHost(
            navController = navController,
            startDestination = ChallengeRegisterNavScreen.RegisterAuth.route
        ) {
            composable(ChallengeRegisterNavScreen.RegisterAuth.route) {
                RegisterAuthScreen(clickListener = registerClickListener)
            }

            composable(ChallengeRegisterNavScreen.ChallengeGoals.route) {
                ChallengeGoals(clickListener = registerClickListener)
            }

            composable(ChallengeRegisterNavScreen.ChallengeOpen.route) {
                ChallengeOpenScreen(
                    clickListener = registerClickListener,
                    challengeAuth = challengeDataState?.is_verification_time,
                    viewModel = viewModel
                )
            }

            composable(ChallengeRegisterNavScreen.ChallengerRecruitment.route) {
                ChallengerRecruitment(clickListener = registerClickListener)
            }
        }
    }

    private fun init() {
    }

    private fun onBack() {
        if (navController.currentBackStackEntry?.destination?.route == ChallengeRegisterNavScreen.RegisterAuth.route) {
            finish()
        }
        navController.popBackStack()
    }

    private fun initClickListener() {
        registerClickListener = object : RegisterClickListener {
            override fun onClickAuthNext(auth: String) {
                viewModel.selectAuth(auth)
                navController.navigate(ChallengeRegisterNavScreen.ChallengeOpen.route)
            }

            override fun onClickOpenNext(
                startDay: String,
                perWeek: String,
                verificationPeriodType: String
            ) {
                val week = perWeek.replace("[^0-9]".toRegex(), "")
                val type: String = when (verificationPeriodType) {
                    "매일인증" -> {
                        "daily"
                    }
                    "평일만 인증(월,화,수,목,금)" -> {
                        "weekday"
                    }
                    "주말만 인증 (토,일)" -> {
                        "weekend"
                    }
                    else -> {
                        "per_week"
                    }
                }
                viewModel.verificationPeriodType(startDay, week, type)
                navController.navigate(ChallengeRegisterNavScreen.ChallengerRecruitment.route)

            }

            override fun onClickRecruitmentNext() {
                navController.navigate(ChallengeRegisterNavScreen.ChallengeGoals.route)
            }

            override fun onClickChallengeCreate(auth: String, precautions: String, imgUrl: String) {
//                viewModel.createChallenge(challengeData)
                viewModel.challengeGoals(auth, precautions, imgUrl)
            }

            override fun onClickSelectedAuth(auth: String) {

            }

            override fun onClickDropDownItem(item: String) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun observeViewModel() {
        viewModel.isChallengeCreate.observe(this) {
            if (it) {
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

sealed class ChallengeRegisterNavScreen(val route: String) {
    object RegisterAuth : ChallengeRegisterNavScreen("RegisterAuth")
    object ChallengeGoals : ChallengeRegisterNavScreen("ChallengeGoals")
    object ChallengeOpen : ChallengeRegisterNavScreen("ChallengeOpen")
    object ChallengerRecruitment : ChallengeRegisterNavScreen("ChallengerRecruitment")
}