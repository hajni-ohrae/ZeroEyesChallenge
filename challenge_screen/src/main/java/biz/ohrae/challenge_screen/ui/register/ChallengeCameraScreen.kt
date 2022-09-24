package biz.ohrae.challenge_screen.ui.register

import android.content.ContentValues
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.UseCase
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import biz.ohrae.challenge_screen.ui.camera.CameraPreview
import biz.ohrae.challenge_screen.util.executor
import biz.ohrae.challenge_screen.util.getCameraProvider
import kotlinx.coroutines.launch
import timber.log.Timber


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengeCameraScreen(
    capturedCallback: ImageCapture.OnImageSavedCallback? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ConstraintCamera(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            capturedCallback = capturedCallback
        )
    }
}

@Composable
private fun ConstraintCamera(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    capturedCallback: ImageCapture.OnImageSavedCallback? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    var previewUseCase by remember { mutableStateOf<UseCase>(androidx.camera.core.Preview.Builder().build()) }
    val imageCaptureUseCase by remember {
        mutableStateOf(
            ImageCapture.Builder()
                .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()
        )
    }

    LaunchedEffect(previewUseCase) {
        val cameraProvider = context.getCameraProvider()
        try {
            // Must unbind the use-cases before rebinding them.
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
            )
        } catch (ex: Exception) {
            Timber.e( "Failed to bind camera use cases", ex)
        }
    }

    ConstraintLayout(
        modifier = Modifier
    ) {
        val (camera, box1, box2, button) = createRefs()

        CameraPreview(
            modifier = modifier
                .constrainAs(camera) {
                    top.linkTo(parent.top)
                    bottom.linkTo(button.top)
                },
            onUseCase = {
                previewUseCase = it
            }
        )
        CameraButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                },
            onClick = {
                coroutineScope.launch {
                    if (capturedCallback != null) {
                        val currentTime = System.currentTimeMillis().toString() + ".jpg"
                        val contentValues = ContentValues()
                        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        contentValues.put(
                            MediaStore.Images.Media.DISPLAY_NAME,
                            currentTime
                        )
                        val options = ImageCapture.OutputFileOptions.Builder(
                            context.contentResolver,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues
                        ).build()

                        imageCaptureUseCase.takePicture(options, context.executor, capturedCallback)
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .background(Color(0xff0d0d0d).copy(alpha = 0.5f))
                .constrainAs(box1) {
                    top.linkTo(parent.top)
                }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .background(Color(0xff0d0d0d).copy(alpha = 0.5f))
                .constrainAs(box2) {
                    bottom.linkTo(button.top)
                }
        )
    }
}

@Composable
private fun CameraButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .aspectRatio(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.White
            ),
            shape = CircleShape,
            onClick = {
                onClick()
            },
            contentPadding = PaddingValues(0.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(0.83f)
                        .aspectRatio(1f),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color.Black),
                    color = Color.White
                ) {}
            }
        }
    }
}
