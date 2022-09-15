package biz.ohrae.challenge_screen.ui.main

import android.content.Intent
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
import biz.ohrae.challenge.ui.components.header.Header
import dagger.hilt.android.AndroidEntryPoint
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailActivity
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeActivity
import biz.ohrae.challenge_screen.ui.register.RegisterActivity
import biz.ohrae.challenge_screen.ui.register.RegisterClickListener

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeMainViewModel
    private lateinit var mainClickListener: MainClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeMainViewModel::class.java]

        init()
        initClickListener()
        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    @Composable
    private fun BuildContent() {
        val mainScreenState by viewModel.mainScreenState.observeAsState()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(goMyChallenge = { goMyChallenge() })
            if (mainScreenState != null) {
                ChallengeMainScreen(
                    mainScreenState = mainScreenState,
                    clickListener = mainClickListener
                )
            }
        }
    }

    private fun init() {
        viewModel.getChallengeList()
    }

    private fun initClickListener() {
        mainClickListener = object : MainClickListener {
            override fun onClickPurchaseTicket() {
                TODO("Not yet implemented")
            }

            override fun onClickRegister() {
                val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            override fun onClickChallengeItem(id: String) {
                goDetail(id)
            }
        }
    }

    private fun goDetail(id: String) {
        val intent = Intent(this, ChallengeDetailActivity::class.java)
        intent.putExtra("challengeId", id)
        startActivity(intent)
    }

    private fun goMyChallenge() {
        val intent = Intent(this, MyChallengeActivity::class.java)
        startActivity(intent)
    }
}

sealed class ChallengeNavScreen(val route: String) {
    object ChallengeMain : ChallengeNavScreen("ChallengeMain")
}