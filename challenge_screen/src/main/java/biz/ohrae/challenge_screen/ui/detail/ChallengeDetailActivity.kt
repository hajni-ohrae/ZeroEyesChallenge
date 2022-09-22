package biz.ohrae.challenge_screen.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.NavigationHeader
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_screen.ui.participation.ParticipationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeDetailViewModel
    private lateinit var navController: NavHostController
    private var challengeId: String? = null
    private lateinit var detailClickListener: ChallengeDetailClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeDetailViewModel::class.java]

        challengeId = intent.getStringExtra("challengeId")

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
                val challengeData by viewModel.challengeData.observeAsState()
                ChallengeDetailScreen(
                    challengeData = challengeData,
                    clickListener = detailClickListener
                )
            }
        }
    }

    private fun init() {
        viewModel.getChallenge(challengeId.toString())
    }

    private fun initClickListener() {
        detailClickListener = object : ChallengeDetailClickListener {
            override fun onClickParticipation() {
                intent = Intent(this@ChallengeDetailActivity, ParticipationActivity::class.java)
                intent.putExtra("challengeId", challengeId)
                startActivity(intent)
            }
        }
    }
}

sealed class ChallengeDetailNavScreen(val route: String) {
    object DetailBeforeJoin : ChallengeDetailNavScreen("DetailBeforeJoin")
}