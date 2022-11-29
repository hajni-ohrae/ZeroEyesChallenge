package biz.ohrae.challenge_screen.ui.challengers

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailNavScreen
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailViewModel
import biz.ohrae.challenge_screen.ui.dialog.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengersActivity: BaseActivity() {
    private lateinit var viewModel: ChallengeDetailViewModel

    private var challengeId: String? = null
    private var type: String? = null
    private var authType: String? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeDetailViewModel::class.java]
        challengeId = intent.getStringExtra("challengeId")
        type = intent.getStringExtra("type")
        authType = intent.getStringExtra("authType")
        userId = prefs.getUserData()?.id

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

        initClickListeners()
        observeViewModels()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @Composable
    fun BuildContent() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackButton(
                title = "랭킹",
                isShare = false,
                onBack = { onBack() },
                onShare = {}
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
        val navController = rememberNavController()
        val challengers by viewModel.challengers.observeAsState()
        val challengeData by viewModel.challengeData.observeAsState()

        NavHost(
            navController = navController,
            startDestination = ChallengersNavScreen.Challengers.route
        ) {
            composable(ChallengersNavScreen.Challengers.route) {
                ChallengersScreen(
                    challengers = challengers,
                    challengeData = challengeData,
                    userId = userId,
                    type = type,
                    authType = authType
                )
            }
        }
    }


    private fun init() {
        viewModel.getUserByChallenge(challengeId.toString())
    }

    override fun onBack() {
        finish()
    }

    override fun initClickListeners() {

    }

    override fun observeViewModels() {

    }
}

sealed class ChallengersNavScreen(val route: String) {
    object Challengers : ChallengersNavScreen("Challengers")
}