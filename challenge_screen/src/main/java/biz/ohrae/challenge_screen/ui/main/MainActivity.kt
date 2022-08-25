package biz.ohrae.challenge_screen.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import biz.ohrae.challenge.ui.theme.ChallengeInTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeMainViewModel::class.java]

        init()
        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    @Composable
    private fun BuildContent() {
        val challengeData by viewModel.challengeData.observeAsState()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (challengeData != null) {
                ChallengeMainScreen(challengeData = challengeData)
            }
        }
    }

    private fun init() {
        viewModel.getChallengeList()
    }

}

sealed class ChallengeNavScreen(val route: String) {
    object ChallengeMain : ChallengeNavScreen("ChallengeMain")
}