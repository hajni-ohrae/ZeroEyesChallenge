package biz.ohrae.challenge_screen.ui.mychallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_repo.util.prefs.SharedPreference

class MyChallengeActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreference

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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            MyChallengeScreen(user = prefs.getUserData())
        }
    }
}