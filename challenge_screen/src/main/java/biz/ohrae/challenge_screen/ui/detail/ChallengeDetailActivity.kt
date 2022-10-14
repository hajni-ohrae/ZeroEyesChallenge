package biz.ohrae.challenge_screen.ui.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import biz.ohrae.challenge_screen.ui.participation.ParticipationActivity
import biz.ohrae.challenge_screen.ui.register.ChallengeCameraScreen
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ChallengeDetailActivity : BaseActivity() {
    private lateinit var viewModel: ChallengeDetailViewModel
    private lateinit var navController: NavHostController
    private var challengeId: String? = null
    private lateinit var detailClickListener: ChallengeDetailClickListener
    private lateinit var capturedCallback: ImageCapture.OnImageSavedCallback

    private lateinit var launcher: ActivityResultLauncher<Intent>

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

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                finish()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        } else {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
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
                val challengeVerificationState by viewModel.challengeVerificationState.observeAsState()

                ChallengeJoinedDetailScreen(
                    challengeData = challengeData,
                    challengers = challengers,
                    verificationState = challengeVerificationState,
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
        viewModel.getChallenge(challengeId.toString())
        viewModel.getUserByChallenge(challengeId.toString(), 1, 11)
    }

    override fun onBack() {
        if (navController.currentBackStackEntry?.destination?.route == ChallengeDetailNavScreen.Detail.route) {
            finish()
        } else {
            navController.popBackStack()
        }
    }

    override fun initClickListeners() {
        // camera capture callback
        capturedCallback = object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Timber.e("onCaptureSuccess : ${outputFileResults.savedUri}")
                viewModel.setChallengeAuthImage(outputFileResults.savedUri.toString())
                navController.navigate(ChallengeDetailNavScreen.AuthCameraResult.route)
            }

            override fun onError(exception: ImageCaptureException) {
                Timber.e("onCaptureError")
            }
        }

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

            override fun onClickAuth() {
                val permission = Manifest.permission.CAMERA
                if(ContextCompat.checkSelfPermission(this@ChallengeDetailActivity, permission)
                    != PackageManager.PERMISSION_GRANTED)
                {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(this@ChallengeDetailActivity, arrayOf(permission), 100)
                } else {
                    navController.navigate(ChallengeDetailNavScreen.AuthCameraPreview.route)
                }
            }

            override fun onClickReTakePhoto() {
                navController.navigate(ChallengeDetailNavScreen.AuthCameraPreview.route)
            }

            override fun onClickUsePhoto() {
                navController.navigate(ChallengeDetailNavScreen.AuthWrite.route)
            }

            override fun onClickRedCardInfo() {
                navController.navigate(ChallengeDetailNavScreen.RedCardInfo.route)
            }

            override fun onDone(content: String) {
                viewModel.verifyChallenge(content)
                navController.navigate(ChallengeDetailNavScreen.JoinedDetail.route)
            }
        }
    }

    override fun observeViewModels() {
        viewModel.challengeData.observe(this) {
            viewModel.isLoading(false)
        }
    }
}

sealed class ChallengeDetailNavScreen(val route: String) {
    object Detail : ChallengeDetailNavScreen("Detail")
    object JoinedDetail : ChallengeDetailNavScreen("JoinedDetail")
    object RedCardInfo : ChallengeDetailNavScreen("RedCardInfo")
    object AuthCameraPreview : ChallengeDetailNavScreen("AuthCameraPreview")
    object AuthCameraResult : ChallengeDetailNavScreen("AuthCameraResult")
    object AuthWrite : ChallengeDetailNavScreen("AuthWrite")
}