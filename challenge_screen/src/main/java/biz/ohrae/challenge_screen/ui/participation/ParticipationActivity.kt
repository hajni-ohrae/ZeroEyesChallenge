package biz.ohrae.challenge_screen.ui.participation

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
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_screen.ui.register.ChallengeRegisterNavScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParticipationActivity : AppCompatActivity() {
    private var challengeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        challengeId = intent.getStringExtra("challengeId")

        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    @Composable
    private fun BuildContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultWhite)
        ) {
            BackButton(onBack = { onBack() },"챌린지 신청")
            Column(modifier = Modifier) {
                ParticipationScreen()
            }
        }
    }

    private fun init() {
    }

    private fun onBack() {
        finish()
    }
}