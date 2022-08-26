package biz.ohrae.challenge_screen.ui.register

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeRegisterViewModel

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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }

    private fun init() {
    }
}

sealed class ChallengeRegisterNavScreen(val route: String) {
    object ChallengeRegister1 : ChallengeRegisterNavScreen("ChallengeRegister1")
    object ChallengeRegister2 : ChallengeRegisterNavScreen("ChallengeRegister2")
    object ChallengeRegister3 : ChallengeRegisterNavScreen("ChallengeRegister3")
    object ChallengeRegister4 : ChallengeRegisterNavScreen("ChallengeRegister4")
}