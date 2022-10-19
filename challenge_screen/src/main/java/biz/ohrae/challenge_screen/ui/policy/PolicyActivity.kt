package biz.ohrae.challenge_screen.ui.policy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_screen.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyActivity: BaseActivity() {
    private var policyType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }

        policyType = intent.getStringExtra("policyType")

        initClickListeners()
        observeViewModels()
    }

    @Composable
    fun BuildContent() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackButton(
                title = "",
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
        val startDestination = if (policyType == "Caution") {
            PolicyNavScreen.Caution.route
        } else {
            PolicyNavScreen.Refund.route
        }

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(PolicyNavScreen.Caution.route) {
                PolicyCautionScreen()
            }
            composable(PolicyNavScreen.Refund.route) {
                PolicyRefundScreen()
            }
        }
    }

    override fun onBack() {
        finish()
    }
}

sealed class PolicyNavScreen(val route: String) {
    object Caution : PolicyNavScreen("Caution")
    object Refund : PolicyNavScreen("Refund")
}