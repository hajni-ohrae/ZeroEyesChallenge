package biz.ohrae.challenge_screen.ui.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.NavigationHeader
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeDetailViewModel
    private lateinit var navController: NavHostController
    private var challengeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeDetailViewModel::class.java]

        challengeId = intent.getStringExtra("challengeId")

        init()
        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    @Composable
    private fun BuildContent() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NavigationHeader(
                onClickBack = {},
                onClickShare = {}
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Navigation()
            }
        }
    }

    @Composable
    private fun Navigation() {
        navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = ChallengeDetailNavScreen.DetailBeforeJoin.route
        ) {
            composable(ChallengeDetailNavScreen.DetailBeforeJoin.route) {
                ChallengeDetailBeforeJoinScreen(
                    viewModel = viewModel
                )
            }
        }
    }

    private fun init() {
        viewModel.getChallenge(challengeId.toString())
    }

}

sealed class ChallengeDetailNavScreen(val route: String) {
    object DetailBeforeJoin : ChallengeDetailNavScreen("DetailBeforeJoin")
}