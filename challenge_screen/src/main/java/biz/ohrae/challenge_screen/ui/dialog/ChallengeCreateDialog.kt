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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.window.layout.WindowMetricsCalculator
import biz.ohrae.challenge.ui.components.button.ArrowTextButton
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

class ChallengeCreateDialog() : DialogFragment() {
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
                ChallengeCreateDialog(
                    listener = dialogListener,
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
        params?.width = (width * 0.88f).toInt()
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
private fun ChallengeCreateDialog(
    listener: CustomDialogListener? = null,
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
                    .padding(20.dp, 30.dp),
            ) {
                Text(
                    text = "챌린지를 개설하기 전 꼭 \n" +
                            "확인해주세요",
                    fontSize = dpToSp(dp = 20.dp),
                    lineHeight = dpToSp(dp = 24.dp),
                    style = myTypography.extraBold,
                    color = TextBlack
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "챌린지를 개설하면 꼭 참가해야 해요\n" +
                            "참가하지 않으면 챌린지가 개설되지 않습니다",
                    fontSize = dpToSp(dp = 16.dp),
                    lineHeight = dpToSp(dp = 19.2.dp),
                    style = myTypography.extraBold,
                    color = Color(0xff6c6c6c)
                )
                Spacer(modifier = Modifier.height(14.dp))
                ArrowTextButton(
                    text = "유의사항 더보기",
                    onClick = {
                        listener?.clickNegative()
                    }
                )
            }
            FlatBottomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                text = "동의하고 개설",
                onClick = {
                    listener?.clickPositive()
                },
            )
        }
    }
}

