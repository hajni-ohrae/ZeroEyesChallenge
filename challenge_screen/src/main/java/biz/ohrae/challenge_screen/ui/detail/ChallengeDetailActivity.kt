package biz.ohrae.challenge_screen.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeDetailViewModel::class.java]

        challengeId = intent.getStringExtra("challengeId")

        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }

        initClickListener()
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        init()
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
        val isJoined by viewModel.isJoined.observeAsState()
        val challengeData by viewModel.challengeData.observeAsState()

        if (isJoined == null || challengeData == null) {
            return
        }

        NavHost(
            navController = navController,
            startDestination = if (isJoined == true) ChallengeDetailNavScreen.JoinedDetail.route else ChallengeDetailNavScreen.Detail.route
        ) {
            composable(ChallengeDetailNavScreen.Detail.route) {
                ChallengeDetailScreen(
                    challengeData = challengeData,
                    clickListener = detailClickListener
                )
            }
            composable(ChallengeDetailNavScreen.JoinedDetail.route) {
                val challengers by viewModel.challengers.observeAsState()
                ChallengeJoinedDetailScreen(
                    challengeData = challengeData,
                    challengers = challengers,
                    clickListener = detailClickListener
                )
            }
        }
    }

    private fun init() {
        viewModel.getChallenge(challengeId.toString())
        viewModel.getUserByChallenge(challengeId.toString(), 1, 11)
    }

    private fun initClickListener() {
        detailClickListener = object : ChallengeDetailClickListener {
            override fun onClickParticipation() {
                intent = Intent(this@ChallengeDetailActivity, ParticipationActivity::class.java)
                intent.putExtra("challengeId", challengeId)
                intent.putExtra(
                    "isCancel",
                    !viewModel.challengeData.value?.inChallenge.isNullOrEmpty()
                )
                launcher.launch(intent)
            }
        }
    }
}

sealed class ChallengeDetailNavScreen(val route: String) {
    object Detail : ChallengeDetailNavScreen("Detail")
    object JoinedDetail : ChallengeDetailNavScreen("JoinedDetail")
}