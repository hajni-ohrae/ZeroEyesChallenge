package biz.ohrae.challenge_screen.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.window.layout.WindowMetricsCalculator
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.components.filter.ChallengeFilterItem
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import com.google.accompanist.flowlayout.MainAxisAlignment

class FilterDialog() :
    DialogFragment() {
    private lateinit var filterDialogListener: FilterDialogListener

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
                Filter(
                    listener = filterDialogListener,
                    "초기화",
                    "100개 챌린지 보기"
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
//        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.Transparent))
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

    fun setListener(listener: FilterDialogListener) {
        this.filterDialogListener = listener
    }
}

@Preview(
    widthDp = 800,
    showBackground = true
)
@Composable
fun Filter(
    listener: FilterDialogListener? = null,
    positiveBtnName: String = "확인",
    negativeBtnName: String = "취소",
) {
    val certifiedList = listOf("매일", "평일만", "주말만", "주1회", "주2회", "주3회", "주4회", "주5회", "주6회")
    val periodList = listOf("1주", "2주", "3주")
    val etcList = listOf("18세 미만 참여불가")
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
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
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "챌린저 모집",
                        style = myTypography.w500,
                        fontSize = dpToSp(dp = 20.dp),
                        color = DefaultBlack
                    )
                    TextButton(onClick = { listener?.clickInitialization() }) {
                        Text(
                            text = "취소",
                            color = Color(0xff747474),
                            style = myTypography.w500,
                            fontSize = dpToSp(dp = 20.dp),
                        )
                    }
                }

                ChallengeFilterItem(modifier = Modifier, title = "인증빈도", list = certifiedList)
                ChallengeFilterItem(modifier = Modifier, title = "챌린지 기간", list = periodList)
                ChallengeFilterItem(modifier = Modifier, title = "기타", list = etcList)
            }


            FlatDoubleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                rightText = "홈으로",
                leftText = "챌린지 알람 설정",
                onClickRight = {
                },
                onClickLeft = {}
            )
        }
    }
}

