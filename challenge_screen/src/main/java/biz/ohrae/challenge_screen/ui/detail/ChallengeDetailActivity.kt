package biz.ohrae.challenge_screen.ui.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import biz.ohrae.challenge_screen.ui.challengers.ChallengersActivity
import biz.ohrae.challenge_screen.ui.dialog.LoadingDialog
import biz.ohrae.challenge_screen.ui.mychallenge.PolicyScreen
import biz.ohrae.challenge_screen.ui.participation.ParticipationActivity
import biz.ohrae.challenge_screen.ui.register.ChallengeCameraScreen
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ChallengeDetailActivity : BaseActivity() {
    private lateinit var viewModel: ChallengeDetailViewModel
    private lateinit var navController: NavHostController
    private lateinit var detailClickListener: ChallengeDetailClickListener
    private lateinit var capturedCallback: ImageCapture.OnImageSavedCallback

    private var challengeId: String? = null
    private var isParticipant: Boolean = false

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeDetailViewModel::class.java]

        challengeId = intent.getStringExtra("challengeId")
        isParticipant = intent.getBooleanExtra("isParticipant", false)

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
                    clickListener = detailClickListener,
                    isParticipant = isParticipant
                )
            }
            composable(ChallengeDetailNavScreen.JoinedDetail.route) {
                val challengers by viewModel.challengers.observeAsState()
                val challengeVerifiedList by viewModel.challengeVerifiedList.observeAsState()
                val challengeVerificationState by viewModel.challengeVerificationState.observeAsState()

                ChallengeJoinedDetailScreen(
                    challengeData = challengeData,
                    challengers = challengers,
                    challengeVerifiedList = challengeVerifiedList,
                    verificationState =  challengeVerificationState,
                    clickListener = detailClickListener,
                    onBottomReached = {
                        onBottomReached()
                    }
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
        viewModel.getVerifyList(challengeId.toString())
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
                if (outputFileResults.savedUri != null) {
                    viewModel.setChallengeAuthImage(outputFileResults.savedUri!!)
                    navController.navigate(ChallengeDetailNavScreen.AuthCameraResult.route)
                }
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
                val permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )

                val permissionResults = mutableListOf<String>()
                permissions.forEach {
                    val result = ContextCompat.checkSelfPermission(this@ChallengeDetailActivity, it)
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionResults.add(it)
                    }
                }

                if (permissionResults.isNotEmpty()) {
                    ActivityCompat.requestPermissions(this@ChallengeDetailActivity, permissionResults.toTypedArray(), 100)
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

            override fun onClickShowAllChallengers() {
                val intent = Intent(this@ChallengeDetailActivity, ChallengersActivity::class.java)
                intent.putExtra("challengeId", challengeId)
                startActivity(intent)
            }

            override fun onDone(content: String) {
                viewModel.challengeAuthImageUri.value?.let {
                    viewModel.isLoading(true)
                    viewModel.verifyChallenge(content, uriToFilePath(it))
                }
            }

            override fun onClickAuthItemCard() {
                val intent = Intent(this@ChallengeDetailActivity, ChallengeAuthFeedActivity::class.java)
                intent.putExtra("challengeId", challengeId)
                startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var grantedCount = 0
        if (requestCode == 100) {
            if (grantResults.isNotEmpty()) {
                grantResults.forEach {
                    if (it == PackageManager.PERMISSION_GRANTED) {
                        grantedCount++
                    }
                }

                if (grantedCount == grantResults.size) {
                    navController.navigate(ChallengeDetailNavScreen.AuthCameraPreview.route)
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Permission Denied", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Permission Denied", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun observeViewModels() {
        viewModel.challengeData.observe(this) {
            viewModel.isLoading(false)
        }

        viewModel.verified.observe(this) {
            viewModel.isLoading(false)
            if (it == true) {
                init()
                navController.navigate(ChallengeDetailNavScreen.JoinedDetail.route) {
                    popUpTo(0)
                }
            } else {
                val errorData = viewModel.errorData.value
                showSnackBar(errorData?.code, errorData?.message)
            }
        }
    }

    private fun onBottomReached() {

    }

    @SuppressLint("Range")
    private fun uriToFilePath(contentUri: Uri): String {
        Timber.e("contentUri : $contentUri")
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var path = ""

        try {
            val cursor = contentResolver.query(contentUri, proj, null, null, null)
            if (cursor != null) {
                cursor.moveToNext()
                path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))

                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return path
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