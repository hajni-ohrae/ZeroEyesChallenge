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
import androidx.activity.compose.BackHandler
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
import biz.ohrae.challenge_screen.ui.dialog.ConfirmDialog
import biz.ohrae.challenge_screen.ui.dialog.CustomDialogListener
import biz.ohrae.challenge_screen.ui.dialog.LoadingDialog
import biz.ohrae.challenge_screen.ui.mychallenge.PolicyScreen
import biz.ohrae.challenge_screen.ui.participation.ParticipationActivity
import biz.ohrae.challenge_screen.ui.policy.PolicyActivity
import biz.ohrae.challenge_screen.ui.register.ChallengeCameraScreen
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import me.echodev.resizer.Resizer
import timber.log.Timber
import java.io.File
import java.net.URLEncoder


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

        challengeId = intent.getStringExtra("challengeId")
        isParticipant = intent.getBooleanExtra("isParticipant", false)

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
                onShare = {
                    onShare()
                }
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
        val isFinished by viewModel.isFinished.observeAsState()
        val challengeData by viewModel.challengeData.observeAsState()

        if (isJoined == null || challengeData == null) {
            return
        }
        val startPage =
            if (isFinished == true) {
                ChallengeDetailNavScreen.Finished.route
            } else {
                if (isJoined == true) ChallengeDetailNavScreen.JoinedDetail.route else ChallengeDetailNavScreen.Detail.route
            }

        NavHost(
            navController = navController,
            startDestination = startPage
        ) {
            composable(ChallengeDetailNavScreen.Detail.route) {
                val challengers by viewModel.challengers.observeAsState()

                ChallengeDetailScreen(
                    challengeData = challengeData,
                    challengers = challengers,
                    clickListener = detailClickListener,
                    isParticipant = isParticipant,
                    viewModel = viewModel
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
                    verificationState = challengeVerificationState,
                    clickListener = detailClickListener,
                    onBottomReached = {
                        onBottomReached()
                    }
                )
            }
            composable(ChallengeDetailNavScreen.AuthCameraPreview.route) {
                BackHandler(true) {
                    onBack()
                }
                ChallengeCameraScreen(
                    capturedCallback = capturedCallback
                )
            }
            composable(ChallengeDetailNavScreen.AuthCameraResult.route) {
                BackHandler(true) {
                    onBack()
                }
                val challengeAuthImageUri by viewModel.challengeAuthImageUri.observeAsState()
                ChallengeDetailAuthCameraResultScreen(
                    imageUri = challengeAuthImageUri,
                    clickListener = detailClickListener
                )
            }
            composable(ChallengeDetailNavScreen.AuthWrite.route) {
                BackHandler(true) {
                    onBack()
                }
                ChallengeDetailAuthWriteScreen(
                    clickListener = detailClickListener
                )
            }
            composable(ChallengeDetailNavScreen.RedCardInfo.route) {
                PolicyScreen(screen = "")
            }
            composable(ChallengeDetailNavScreen.Finished.route) {
                ChallengeFinishedScreen(challengeData = challengeData)
            }
        }
    }

    private fun init() {
        viewModel.isLoading(true)
        viewModel.getChallenge(challengeId.toString())
        viewModel.getChallengeResult(challengeId.toString())
        viewModel.getUserByChallenge(challengeId.toString(), 1, 11)
        viewModel.getVerifyList(challengeId.toString(), isInit = true, "desc", 0)
    }

    override fun onBack() {
        when (navController.currentBackStackEntry?.destination?.route) {
            ChallengeDetailNavScreen.Detail.route,
            ChallengeDetailNavScreen.JoinedDetail.route -> {
                finish()
            }
            ChallengeDetailNavScreen.AuthCameraPreview.route,
            ChallengeDetailNavScreen.AuthCameraResult.route,
            ChallengeDetailNavScreen.AuthWrite.route -> {
                showAuthCancelDialog()
            }
            else -> {
                navController.popBackStack()
            }
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
                viewModel.challengeData.value?.let {
                    if (it.status == "register") {
                        intent =
                            Intent(this@ChallengeDetailActivity, ParticipationActivity::class.java)
                        intent.putExtra("challengeId", challengeId)
                        intent.putExtra("isCancel", !it.inChallenge.isNullOrEmpty())
                        launcher.launch(intent)
                    } else {
                        showSnackBar("모집종료 되었습니다.")
                    }
                }
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
                    ActivityCompat.requestPermissions(
                        this@ChallengeDetailActivity,
                        permissionResults.toTypedArray(),
                        100
                    )
                } else {
                    navController.navigate(ChallengeDetailNavScreen.AuthCameraPreview.route)
                }
            }

            override fun onClickBookMark(like: Boolean) {
                val fa: Int = if (like) {
                    1
                } else {
                    0
                }
                viewModel.favoriteChallenge(challengeId.toString(), fa)
//                showSnackBar("준비중입니다.")
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

                    val imagePath = uriToFilePath(it)
                    val originFile = File(imagePath)
                    val resizedImage = Resizer(this@ChallengeDetailActivity)
                        .setTargetLength(1080)
                        .setQuality(80)
                        .setOutputFormat("JPEG")
                        .setOutputFilename("resized_image")
                        .setOutputDirPath(originFile.parent)
                        .setSourceImage(originFile)
                        .resizedFile

                    viewModel.verifyChallenge(content, resizedImage.path)
                }
            }

            override fun onClickAuthItemCard() {
                val intent =
                    Intent(this@ChallengeDetailActivity, ChallengeAuthFeedActivity::class.java)
                intent.putExtra("challengeId", challengeId)
                startActivity(intent)
            }

            override fun onClickCaution() {
                val intent = Intent(this@ChallengeDetailActivity, PolicyActivity::class.java)
                intent.putExtra("policyType", "Caution")
                startActivity(intent)
            }

            override fun onClickChallengersResults() {

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
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Permission Denied",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Permission Denied",
                    Snackbar.LENGTH_SHORT
                ).show()
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

    private fun onShare() {
//        val dynamicLink = Firebase.dynamicLinks.dynamicLink {
//            link = Uri.parse("https://challenge.mooin.kr?id=$challengeId")
//            domainUriPrefix = "https://mooin.page.link"
//            // Open links with this app on Android
//            androidParameters {
//            }
//            // Open links with com.example.ios on iOS
//            iosParameters("com.example.ios") { }
//        }

        val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse("https://challenge.mooin.kr")
            longLink = Uri.parse("https://mooin.page.link/?link=" +
                    "https://challenge.mooin.kr/?id=$challengeId&apn=com.example.android&ibn=com.example.ios")
            domainUriPrefix = "https://mooin.page.link"
        }.addOnSuccessListener { (shortLink, flowchartLink) ->
            Timber.e("shortLink : $shortLink, flowchartLink : $flowchartLink")

            val link = shortLink.toString() + "?id=$challengeId"

            // Short link created
            val intent = Intent(Intent.ACTION_SEND)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, link)

            val shareIntent = Intent.createChooser(intent, "공유하기")
            startActivity(shareIntent)
        }.addOnFailureListener {
            // Error
            // ...
            showSnackBar("공유 링크 생성에 실패했습니다.")
        }
//
//        val i = Intent(Intent.ACTION_VIEW, dynamicLink.uri)
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        i.setPackage("com.android.chrome")
//        startActivity(i)
    }

    private fun onBottomReached() {
        viewModel.getVerifyList(id = challengeId.toString())
    }

    private fun showAuthCancelDialog() {
        val dialog =
            ConfirmDialog(positiveBtnName = "계속 작성", content = "인증이 완료되지 않았습니다\n인증을 취소하시겠습니까?")
        dialog.isCancelable = false
        dialog.setListener(object : CustomDialogListener {
            override fun clickPositive() {
                dialog.dismiss()
            }

            override fun clickNegative() {
                dialog.dismiss()
                navController.navigate(ChallengeDetailNavScreen.JoinedDetail.route) {
                    popUpTo(0)
                }
            }
        })
        dialog.show(supportFragmentManager, "AuthCancelDialog")
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
    object Finished : ChallengeDetailNavScreen("Finished")
}