package biz.ohrae.challenge_screen.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_screen.ui.BaseActivity

class BannerDetailActivity : BaseActivity() {
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
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultWhite)
        ) {
            BackButton(
                isDark = false,
                onBack = { onBack() }
            )
            Column(modifier = Modifier) {
                BannerDetailScreen()
            }
        }
    }

    override fun onBack() {
        finish()
    }
}