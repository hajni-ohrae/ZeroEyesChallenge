package biz.ohrae.challenge_screen.ui.register

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_screen.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeRegisterViewModel

    private lateinit var navController: NavHostController
    private lateinit var registerClickListener: RegisterClickListener
    private lateinit var challengeData: ChallengeData
    private lateinit var capturedCallback: ImageCapture.OnImageSavedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeRegisterViewModel::class.java]

        init()
        initClickListener()
        observeViewModel()
        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    @Composable
    private fun BuildContent() {
        navController = rememberNavController()
        var isDark by remember { mutableStateOf(false) }

        LaunchedEffect(navController.currentBackStackEntry?.destination?.route) {
            isDark = (navController.currentBackStackEntry?.destination?.route == ChallengeRegisterNavScreen.ChallengerCameraPreview.route
                    || navController.currentBackStackEntry?.destination?.route == ChallengeRegisterNavScreen.ChallengerCameraResult.route)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultWhite)
        ) {
            BackButton(
                isDark = isDark,
                onBack = { onBack() }
            )
            Column(modifier = Modifier) {
                Navigation()
            }
        }
    }

    @Composable
    private fun Navigation() {
        val challengeDataState by viewModel.challengeData.observeAsState()
        val challengeImageUri by viewModel.challengeImageUri.observeAsState()

        NavHost(
            navController = navController,
            startDestination = ChallengeRegisterNavScreen.RegisterAuth.route
        ) {
            composable(ChallengeRegisterNavScreen.RegisterAuth.route) {
                RegisterAuthScreen(clickListener = registerClickListener)
            }

            composable(ChallengeRegisterNavScreen.ChallengeGoals.route) {
                ChallengeGoals(
                    challengeImageUri = challengeImageUri,
                    clickListener = registerClickListener
                )
            }

            composable(ChallengeRegisterNavScreen.ChallengeOpen.route) {
                ChallengeOpenScreen(
                    clickListener = registerClickListener,
                    challengeAuth = challengeDataState?.is_verification_time,
                    viewModel = viewModel
                )
            }

            composable(ChallengeRegisterNavScreen.ChallengerRecruitment.route) {
                ChallengerRecruitment(clickListener = registerClickListener)
            }

            composable(ChallengeRegisterNavScreen.ChallengerCameraPreview.route) {
                ChallengeCameraScreen(capturedCallback = capturedCallback)
            }

            composable(ChallengeRegisterNavScreen.ChallengerCameraResult.route) {
                ChallengerCameraResultScreen(
                    imageUri = challengeImageUri,
                    clickListener = registerClickListener
                )
            }
        }
    }

    private fun init() {
    }

    private fun onBack() {
        if (navController.currentBackStackEntry?.destination?.route == ChallengeRegisterNavScreen.RegisterAuth.route) {
            finish()
        }
        navController.popBackStack()
    }

    private fun initClickListener() {
        registerClickListener = object : RegisterClickListener {
            override fun onClickAuthNext(auth: String) {
                viewModel.selectAuth(auth)
                navController.navigate(ChallengeRegisterNavScreen.ChallengeOpen.route)
            }

            override fun onClickOpenNext(
                startDay: String,
                perWeek: String,
                verificationPeriodType: String
            ) {
                val week = perWeek.replace("[^0-9]".toRegex(), "")
                val type: String = when (verificationPeriodType) {
                    "매일인증" -> {
                        "daily"
                    }
                    "평일만 인증(월,화,수,목,금)" -> {
                        "weekday"
                    }
                    "주말만 인증 (토,일)" -> {
                        "weekend"
                    }
                    else -> {
                        "per_week"
                    }
                }
                viewModel.verificationPeriodType(startDay, week, type)
                navController.navigate(ChallengeRegisterNavScreen.ChallengerRecruitment.route)
            }

            override fun onClickRecruitmentNext() {
                navController.navigate(ChallengeRegisterNavScreen.ChallengeGoals.route)
            }

            override fun onClickChallengeCreate(auth: String, precautions: String, imgUrl: String) {
//                viewModel.createChallenge(challengeData)
                viewModel.challengeGoals(auth, precautions, imgUrl)
            }

            override fun onClickSelectedAuth(auth: String) {

            }

            override fun onClickPeriod(item: String) {
                viewModel.selectPeriod(item)
            }

            override fun onClickPeriodType(item: String) {
                viewModel.selectPeriodType(item)
            }

            override fun onClickPhotoBox() {
                val permission = Manifest.permission.CAMERA
                if(ContextCompat.checkSelfPermission(this@RegisterActivity, permission)
                    != PackageManager.PERMISSION_GRANTED)
                {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(this@RegisterActivity, arrayOf(permission), 100)
                } else {
                    navController.navigate(ChallengeRegisterNavScreen.ChallengerCameraPreview.route)
                }
            }

            override fun onClickReTakePhoto() {
                navController.navigate(ChallengeRegisterNavScreen.ChallengerCameraPreview.route)
            }

            override fun onClickUsePhoto() {
                navController.navigate(ChallengeRegisterNavScreen.ChallengeGoals.route)
            }
        }

        // camera capture callback
        capturedCallback = object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Timber.e("onCaptureSuccess : ${outputFileResults.savedUri}")
                viewModel.setChallengeImage(outputFileResults.savedUri.toString())
                navController.navigate(ChallengeRegisterNavScreen.ChallengerCameraResult.route)
            }

            override fun onError(exception: ImageCaptureException) {
                Timber.e("onCaptureError")
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isChallengeCreate.observe(this) {
            if (it) {
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
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
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navController.navigate(ChallengeRegisterNavScreen.ChallengerCameraPreview.route)
            } else {
                Snackbar.make(window.decorView, "Camera Permission Denied", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}

sealed class ChallengeRegisterNavScreen(val route: String) {
    object RegisterAuth : ChallengeRegisterNavScreen("RegisterAuth")
    object ChallengeGoals : ChallengeRegisterNavScreen("ChallengeGoals")
    object ChallengeOpen : ChallengeRegisterNavScreen("ChallengeOpen")
    object ChallengerRecruitment : ChallengeRegisterNavScreen("ChallengerRecruitment")
    object ChallengerCameraPreview : ChallengeRegisterNavScreen("ChallengerCameraPreview")
    object ChallengerCameraResult : ChallengeRegisterNavScreen("ChallengerCameraResult")
}