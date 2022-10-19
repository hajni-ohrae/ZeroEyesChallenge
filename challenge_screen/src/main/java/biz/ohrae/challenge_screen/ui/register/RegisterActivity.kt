package biz.ohrae.challenge_screen.ui.register

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
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
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.FileUtils
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.dialog.*
import biz.ohrae.challenge_screen.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {
    companion object {
        const val AUTH: Int = 1
        const val OPEN: Int = 2
        const val CHALLENGER: Int = 3
    }

    private lateinit var viewModel: ChallengeRegisterViewModel

    private lateinit var navController: NavHostController
    private lateinit var registerClickListener: RegisterClickListener
    private lateinit var challengeData: ChallengeData
    private lateinit var capturedCallback: ImageCapture.OnImageSavedCallback
    private lateinit var albumLauncher: ActivityResultLauncher<Intent>

    private var goal: String = ""
    private var caution: String = ""
    private var cameraImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeRegisterViewModel::class.java]

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

        init()
        initClickListeners()
        observeViewModels()

        albumLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Timber.e("data : ${Gson().toJson(result)}")

            if (result.resultCode == RESULT_OK) {
                val data = result.data

                // 사진 결과 받음
                if (data != null && data.data != null) {
                    val selectedImageUri = data.data
                    try {
                        Timber.e("selectedImageUri : $selectedImageUri")
                        viewModel.setChallengeImage(selectedImageUri!!)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    // 카메라로 찍은 사진
                    if (cameraImageUri != null) {
                        viewModel.setChallengeImage(cameraImageUri!!)
                    }
                }
            }
        }
    }

    @Composable
    private fun BuildContent() {
        navController = rememberNavController()
        var isDark by remember { mutableStateOf(false) }

        LaunchedEffect(navController.currentBackStackEntry?.destination?.route) {
            isDark =
                (navController.currentBackStackEntry?.destination?.route == ChallengeRegisterNavScreen.ChallengerCameraPreview.route
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
        val screenState by viewModel.screenState.observeAsState()
        val challengeDataState by viewModel.challengeData.observeAsState()
        val challengeImageUri by viewModel.challengeImageUri.observeAsState()

        NavHost(
            navController = navController,
            startDestination = ChallengeRegisterNavScreen.RegisterAuth.route
        ) {
            composable(ChallengeRegisterNavScreen.RegisterAuth.route) {
                RegisterAuthScreen(
                    clickListener = registerClickListener
                )
            }

            composable(ChallengeRegisterNavScreen.ChallengeOpen.route) {
                ChallengeOpenScreen(
                    challengeOpenState = screenState,
                    clickListener = registerClickListener,
                    challengeData = challengeDataState,
                    viewModel = viewModel
                )
            }

            composable(ChallengeRegisterNavScreen.ChallengerRecruitment.route) {
                ChallengerRecruitment(
                    challengeOpenState = screenState,
                    challengeData = challengeDataState,
                    clickListener = registerClickListener,
                    viewModel = viewModel
                )
            }

            composable(ChallengeRegisterNavScreen.ChallengeGoals.route) {
                ChallengeGoals(
                    challengeImageUri = challengeImageUri,
                    clickListener = registerClickListener
                )
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
        viewModel?.setStartDay(Utils.getDefaultChallengeDate())
    }

    override fun onBack() {
        if (navController.currentBackStackEntry?.destination?.route == ChallengeRegisterNavScreen.RegisterAuth.route) {
            finish()
        }
        navController.popBackStack()
    }

    override fun initClickListeners() {
        registerClickListener = object : RegisterClickListener {
            override fun onClickAuthNext(auth: String) {
                viewModel.selectAuth(auth)
                navController.navigate(ChallengeRegisterNavScreen.ChallengeOpen.route)
            }

            override fun onClickOpenNext(weeks: Int) {
                viewModel.selectPeriod(weeks)
                viewModel.verificationPeriodType()
                navController.navigate(ChallengeRegisterNavScreen.ChallengerRecruitment.route)
            }

            override fun onClickRecruitmentNext() {
                navController.navigate(ChallengeRegisterNavScreen.ChallengeGoals.route)
            }

            override fun onClickChallengeCreate(goal: String, precautions: String, imgUrl: Uri?) {
                if (goal.isEmpty()) {
                    Snackbar.make(
                        this@RegisterActivity,
                        findViewById(android.R.id.content),
                        "챌린지 목표를 입력해주세요.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }

                this@RegisterActivity.goal = goal
                caution = precautions

                if (imgUrl != null) {
                    val imagePath = if (imgUrl.toString()
                            .contains("com.google.android.apps.photos.contentprovider")
                    ) {
                        getPathFromInputStreamUri(this@RegisterActivity, imgUrl)
                    } else {
                        uriToFilePath(imgUrl)
                    }

                    Timber.e("image path : $imagePath")
                    if (!imagePath.isNullOrEmpty()) {
                        viewModel.isLoading(true)
                        viewModel.uploadChallengeImage(imagePath)
                    }
                } else {
                    viewModel.isLoading(true)
                    viewModel.challengeGoals(goal, precautions, null)
                }
            }

            override fun onClickSelectedAuth(auth: String) {

            }

            override fun onClickPeriod(item: DropDownItem) {
                viewModel.selectPeriod(item.value.toInt())
            }

            override fun onClickPeriodType(item: String) {
                viewModel.selectPeriodType(item)
            }

            override fun onClickPhotoBox() {
                val permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )

                val permissionResults = mutableListOf<String>()
                permissions.forEach {
                    val result = ContextCompat.checkSelfPermission(this@RegisterActivity, it)
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionResults.add(it)
                    }
                }

                if (permissionResults.isNotEmpty()) {
                    ActivityCompat.requestPermissions(
                        this@RegisterActivity,
                        permissionResults.toTypedArray(),
                        100
                    )
                } else {
                    callImageSelector()
                }
            }

            override fun onClickReTakePhoto() {
                navController.navigate(ChallengeRegisterNavScreen.ChallengerCameraPreview.route)
            }

            override fun onClickUsePhoto() {
                navController.navigate(ChallengeRegisterNavScreen.ChallengeGoals.route)
            }

            override fun onClickCalendar() {
                val dialog = CalendarDialog(viewModel)
                dialog.setListener(object : CalendarDialogListener {
                    override fun clickPositive() {
                        viewModel.setStartDay(viewModel.selectDay.value.toString())
                        dialog.dismiss()
                    }

                    override fun clickNegative() {
                        dialog.dismiss()
                    }

                    override fun clickDay(day: String) {
                        viewModel?.selectDay(day)
                    }
                })
                dialog.isCancelable = false
                dialog.show(supportFragmentManager, "FilterDialog")
            }

            override fun onClickRecruitDays(item: DropDownItem) {
                viewModel.setRecruitDays(item.value.toInt())
            }

            override fun onClickHoursOfUse(item: String) {
                viewModel.setHourOfUse(item.toInt())
            }
        }

        // camera capture callback
        capturedCallback = object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Timber.e("onCaptureSuccess : ${outputFileResults.savedUri}")
                viewModel.setChallengeImage(outputFileResults.savedUri!!)
                navController.navigate(ChallengeRegisterNavScreen.ChallengerCameraResult.route)
            }

            override fun onError(exception: ImageCaptureException) {
                Timber.e("onCaptureError")
            }
        }
    }

    override fun observeViewModels() {
        viewModel.createdChallengeId.observe(this) {
            viewModel.isLoading(false)
            it?.let {
                showParticipantDialog(it)
            }
        }

        viewModel.uploadedImage.observe(this@RegisterActivity) {
            it?.let {
                Timber.e("imageBucket: ${Gson().toJson(it)}")
                if (!it.errorCode.isNullOrEmpty() || !it.errorMessage.isNullOrEmpty()) {
                    val message =
                        "code : ${it.errorCode.toString()}, message : ${it.errorMessage.toString()}"
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.isLoading(false)
                } else {
                    viewModel.challengeGoals(goal, caution, it.id.toString())
                }
            } ?: run {
                viewModel.isLoading(false)
            }
        }

        viewModel.errorData.observe(this) {
            it?.let {
                showSnackBar(it.code, it.message)
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
                callImageSelector()
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Permission Denied",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
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

    fun getPathFromInputStreamUri(context: Context, uri: Uri): String? {
        var filePath: String? = null
        uri.authority?.let {
            try {
                context.contentResolver.openInputStream(uri).use {
                    val photoFile: File? = FileUtils.createTemporalFileFrom(it)
                    filePath = photoFile?.path
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return filePath
    }

    private fun callImageSelector() {
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_PICK

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val fileName = (System.currentTimeMillis() / 1000).toString() + ".jpg"
        val imageUri = getImageUri(fileName)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraImageUri = imageUri

        val chooser = Intent.createChooser(galleryIntent, "이미지 선택")
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        albumLauncher.launch(chooser)
    }

    private fun getImageUri(fileName: String): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }



    private fun showParticipantDialog(challengeId: String?) {
        val content = "챌린지가 성공적으로 개설되었습니다\n" +
                "개설자도 참여를 해야 챌린지 진행이 가능합니다\n" +
                "\n" +
                "지금 바로 참여하시겠습니까?"
        val dialog =
            ConfirmDialog(positiveBtnName = "지금 바로 참여", negativeBtnName = "나중에", content = content)
        dialog.isCancelable = false
        dialog.setListener(object : CustomDialogListener {
            override fun clickPositive() {
                dialog.dismiss()
                goMain(true, challengeId)
            }

            override fun clickNegative() {
                dialog.dismiss()
                goMain()
            }
        })

        dialog.show(supportFragmentManager, "confirmDialog")
    }

    private fun goMain(isParticipant: Boolean = false, challengeId: String? = null) {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        if (isParticipant) {
            intent.putExtra("challengeId", challengeId)
        }
        startActivity(intent)
        finish()
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