package biz.ohrae.challenge_screen.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.window.layout.WindowMetricsCalculator
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

class ConfirmDialog(
    private var positiveBtnName: String = "확인",
    private var negativeBtnName: String = "취소",
    private var content: String,
) : DialogFragment() {
    private lateinit var dialogListener: CustomDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Confirm(
                    listener = dialogListener,
                    positiveBtnName = positiveBtnName,
                    negativeBtnName = negativeBtnName,
                    content = content,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()

        val windowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(requireActivity())
        val currentBounds = windowMetrics.bounds // E.g. [0 0 1350 1800]
        val width = currentBounds.width()
        val height = currentBounds.height()

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = (width * 0.95f).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }


    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val decorView = dialog!!.window!!.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    fun setListener(listener: CustomDialogListener) {
        this.dialogListener = listener
    }
}

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun Confirm(
    listener: CustomDialogListener? = null,
    positiveBtnName: String = "지금 바로 참여",
    negativeBtnName: String = "나중에",
    content: String = "챌린지가 성공적으로 개설되었습니다\n개설자도 참여를 해야 챌린지 진행이 가능합니다\n\n지금 바로 참여하시겠습니까?"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = DefaultWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DefaultWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 65.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = content,
                    fontSize = dpToSp(dp = 20.dp),
                    style = myTypography.extraBold,
                    color = TextBlack
                )
            }
            FlatDoubleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                leftText = negativeBtnName,
                rightText = positiveBtnName,
                onClickRight = {
                    listener?.clickPositive()
                },
                onClickLeft = {
                    listener?.clickNegative()
                }
            )
        }
    }
}

