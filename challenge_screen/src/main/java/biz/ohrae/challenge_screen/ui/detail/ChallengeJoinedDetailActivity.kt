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
import biz.ohrae.challenge_screen.ui.dialog.LoadingDialog
import biz.ohrae.challenge_screen.ui.mychallenge.PolicyScreen
import biz.ohrae.challenge_screen.ui.register.ChallengeCameraScreen
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ChallengeJoinedDetailActivity : BaseActivity() {
    private lateinit var viewModel: ChallengeDetailViewModel
    private lateinit var navController: NavHostController
    private var challengeId: String? = null

    private lateinit var detailClickListener: ChallengeDetailClickListener
    private lateinit var capturedCallback: ImageCapture.OnImageSavedCallback

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
                isShare = true,
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
        navController = rememberNavController()
        val isJoined by viewModel.isJoined.observeAsState()
        val challengeData by viewModel.challengeData.observeAsState()

        if (isJoined == null || challengeData == null) {
            return
        }

        NavHost(
            navController = navController,
            startDestination = if (isJoined == true) ChallengeDetailNavScreen.JoinedDetail.route else ChallengeDetailNavScreen.JoinedDetail.route
        ) {
            composable(ChallengeDetailNavScreen.JoinedDetail.route) {
                val challengers by viewModel.challengers.observeAsState()
                val challengeVerifiedList by viewModel.challengeVerifiedList.observeAsState()
                Timber.e("challengeVerifiedList : ${Gson().toJson(challengeVerifiedList)}")
                ChallengeJoinedDetailScreen(
                    challengeData = challengeData,
                    challengers = challengers,
                    challengeVerifiedList = challengeVerifiedList,
                    clickListener = detailClickListener
                )
            }
            composable(ChallengeDetailNavScreen.AuthCameraPreview.route) {
                ChallengeCameraScreen(
                    capturedCallback = capturedCallback
                )
            }
            composable(ChallengeDetailNavScreen.AuthCameraResult.route) {
                val challengeAuthImageUri by viewModel.challengeAuthImageUri.observeAsState()

                ChallengeDetailAuthCameraResultScreen(
                    imageUri = challengeAuthImageUri,
                    clickListener = detailClickListener
                )
            }
            composable(ChallengeDetailNavScreen.AuthWrite.route) {
                ChallengeDetailAuthWriteScreen(
                    clickListener = detailClickListener
                )
            }
            composable(ChallengeDetailNavScreen.RedCardInfo.route) {
                PolicyScreen(screen = "")
            }
        }
    }

    private fun init() {
        viewModel.isLoading(true)
    }

    override fun onBack() {
        if (navController.currentBackStackEntry?.destination?.route == ChallengeDetailNavScreen.Detail.route) {
            finish()
        } else {
            navController.popBackStack()
        }
    }

    override fun initClickListeners() {

    }

    override fun observeViewModels() {

    }
}

sealed class ChallengeJoinedDetailNavScreen(val route: String) {
    object JoinedDetail : ChallengeJoinedDetailNavScreen("JoinedDetail")
    object RedCardInfo : ChallengeJoinedDetailNavScreen("RedCardInfo")
    object AuthCameraPreview : ChallengeJoinedDetailNavScreen("AuthCameraPreview")
    object AuthCameraResult : ChallengeJoinedDetailNavScreen("AuthCameraResult")
    object AuthWrite : ChallengeJoinedDetailNavScreen("AuthWrite")
}