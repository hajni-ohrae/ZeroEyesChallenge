package biz.ohrae.challenge_screen.ui.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.dialog.FilterDialog
import biz.ohrae.challenge_screen.ui.dialog.LoadingDialog
import biz.ohrae.challenge_screen.ui.dialog.Report
import biz.ohrae.challenge_screen.ui.dialog.ReportDialog
import biz.ohrae.challenge_screen.ui.mychallenge.PolicyScreen
import biz.ohrae.challenge_screen.ui.register.ChallengeCameraScreen
import biz.ohrae.challenge_screen.ui.register.ChallengeRegisterNavScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeAuthFeedActivity : BaseActivity() {
    private lateinit var viewModel: ChallengeDetailViewModel
    private lateinit var navController: NavHostController
    private var challengeId: String? = null

    private lateinit var authFeedClickListener: ChallengeAuthFeedClickListener
    private lateinit var capturedCallback: ImageCapture.OnImageSavedCallback

    private var order: String = ""
    private var mine: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeDetailViewModel::class.java]

        challengeId = intent.getStringExtra("challengeId")

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
    private fun BuildContent() {
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

    private fun init() {
        viewModel.isLoading(true)
        viewModel.getVerifyList(challengeId.toString(), isInit = true)
        viewModel.getRegisterReport()
    }

    @Composable
    private fun Navigation() {
        navController = rememberNavController()
        val challengeVerifiedList by viewModel.challengeVerifiedList.observeAsState()

        NavHost(
            navController = navController,
            startDestination = ChallengeAuthFeedNavScreen.AuthFeed.route
        ) {
            composable(ChallengeAuthFeedNavScreen.AuthFeed.route) {
                ChallengeAuthFeedScreen(challengeVerifiedList, authFeedClickListener)

            }
        }
    }


    override fun onBack() {
        if (navController.currentBackStackEntry?.destination?.route == ChallengeAuthFeedNavScreen.AuthFeed.route) {
            finish()
        } else {
            navController.popBackStack()
        }
    }

    override fun initClickListeners() {
        authFeedClickListener = object : ChallengeAuthFeedClickListener {
            override fun onClickMine(isMine: Boolean) {
                mine = if (isMine) {
                    1
                } else {
                    0
                }
                viewModel.getVerifyList(challengeId.toString(),true,order,mine)
            }

            override fun onClickOrder(isOrder: Boolean) {
                order = if (!isOrder) {
                    "desc"
                } else {
                    "asc"
                }
                viewModel.getVerifyList(challengeId.toString(),true,order,mine)
            }

            override fun onClickReport() {
                val dialog = ReportDialog(viewModel)
            }

        }
    }

    override fun observeViewModels() {
        viewModel.challengeData.observe(this) {
            viewModel.isLoading(false)
        }
    }
}

sealed class ChallengeAuthFeedNavScreen(val route: String) {
    object AuthFeed : ChallengeAuthFeedNavScreen("AuthFeed")
}