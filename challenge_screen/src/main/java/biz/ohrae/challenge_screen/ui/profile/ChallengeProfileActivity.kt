package biz.ohrae.challenge_screen.ui.profile

import ChallengeProfileScreen
import android.Manifest
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import biz.ohrae.challenge_repo.util.FileUtils
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.niceid.NiceIdActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.echodev.resizer.Resizer
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

@AndroidEntryPoint
class ChallengeProfileActivity : BaseActivity() {
    private lateinit var viewModel: ChallengeProfileViewModel
    private lateinit var navController: NavHostController
    private lateinit var albumLauncher: ActivityResultLauncher<Intent>

    private var clickListener: ChallengeProfileClickListener? = null
    private var headerTitle: String = "프로필"
    private var cameraImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeProfileViewModel::class.java]

        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }

        init()
        initClickListeners()

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
                        viewModel.setProfileImage(selectedImageUri!!)
                        uploadImage(selectedImageUri)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    // 카메라로 찍은 사진
                    if (cameraImageUri != null) {
                        viewModel.setProfileImage(cameraImageUri!!)
                        uploadImage(cameraImageUri!!)
                    }
                }
            }
        }
    }

    private fun init() {
        viewModel.getUserInfo()
    }

    @Composable
    private fun BuildContent() {
        navController = rememberNavController()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultWhite)
        ) {
            BackButton(onBack = { onBack() }, title = headerTitle)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Navigation()
            }
        }
    }

    override fun onBack() {
       finish()
    }

    @Composable
    private fun Navigation() {
        NavHost(
            navController = navController,
            startDestination = ChallengeProfileNavScreen.Profile.route
        ) {
            composable(ChallengeProfileNavScreen.Profile.route) {
                val user by viewModel.user.observeAsState()
                val profileImageUri by viewModel.profileImageUri.observeAsState()

                if (user != null) {
                    ChallengeProfileScreen(
                        user = user!!,
                        profileImageUri = profileImageUri,
                        clickListener = clickListener
                    )
                }
            }
        }
    }

    override fun initClickListeners() {
        clickListener = object : ChallengeProfileClickListener {
            override fun onClickProfileImage() {
                val permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )

                val permissionResults = mutableListOf<String>()
                permissions.forEach {
                    val result = ContextCompat.checkSelfPermission(this@ChallengeProfileActivity, it)
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionResults.add(it)
                    }
                }

                if (permissionResults.isNotEmpty()) {
                    ActivityCompat.requestPermissions(
                        this@ChallengeProfileActivity,
                        permissionResults.toTypedArray(),
                        100
                    )
                } else {
                    callImageSelector()
                }
            }

            override fun onClickIdentityVerification() {
                val intent = Intent(this@ChallengeProfileActivity, NiceIdActivity::class.java)
                intent.putExtra("userId", viewModel.user.value?.id)
                startActivity(intent)
            }
        }
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

    private fun uploadImage(imgUrl: Uri) {
        val imagePath = if (imgUrl.toString().contains("com.google.android.apps.photos.contentprovider")) {
            getPathFromInputStreamUri(this@ChallengeProfileActivity, imgUrl)
        } else {
            uriToFilePath(imgUrl)
        }

        Timber.e("image path : $imagePath")
        if (!imagePath.isNullOrEmpty()) {
            val originFile = File(imagePath)
            val resizedImage = Resizer(this@ChallengeProfileActivity)
                .setTargetLength(1080)
                .setQuality(80)
                .setOutputFormat("JPEG")
                .setOutputFilename("resized_image")
                .setOutputDirPath(originFile.parent)
                .setSourceImage(originFile)
                .resizedFile

            viewModel.isLoading(true)
            viewModel.uploadUserImage(resizedImage.path)
        }
    }

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

    private fun getPathFromInputStreamUri(context: Context, uri: Uri): String? {
        var filePath: String? = null
        uri.authority?.let {
            try {
                context.contentResolver.openInputStream(uri).use {
                    val photoFile: File? = FileUtils.createTemporalFileFrom(it, context)
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
}

sealed class ChallengeProfileNavScreen(val route: String) {
    object Profile : ChallengeProfileNavScreen("Profile")
}