package biz.ohrae.challenge_screen.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.window.layout.WindowMetricsCalculator
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.components.checkBox.MyCheckBox
import biz.ohrae.challenge.ui.components.filter.ChallengeFilterItemList
import biz.ohrae.challenge.ui.components.filter.FeedItem
import biz.ohrae.challenge.ui.components.list_item.ChallengersItem
import biz.ohrae.challenge.ui.components.selectable.LabeledCircleCheck
import biz.ohrae.challenge.ui.theme.*
import biz.ohrae.challenge_repo.model.report.ReportDetail
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.main.FilterState
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailViewModel
import biz.ohrae.challenge_screen.ui.main.ChallengeMainViewModel

class ReportDialog(private val viewModel: ChallengeDetailViewModel) : DialogFragment() {
    private lateinit var reportDialogListener: ReportDialogListener

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
                val reportList by viewModel.reportList.observeAsState()

                Report(
                    listener = reportDialogListener,
                    reportList = reportList
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

    fun setListener(listener: ReportDialogListener) {
        this.reportDialogListener = listener
    }
}

@Preview(
    widthDp = 800,
    showBackground = true
)
@Composable
fun Report(
    listener: ReportDialogListener? = null,
    positiveBtnName: String = "신고하기",
    negativeBtnName: String = "취소",
    userName: String = "하진!",
    user: User? = null,
    reportList: List<ReportDetail>? = null
) {
    var checked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = DefaultWhite
    ) {
        Column(
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 30.dp)
                    .background(DefaultWhite)
            ) {
                Text(
                    text = "신고하기",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 20.dp),
                    color = DefaultBlack
                )
                Spacer(modifier = Modifier.height(20.dp))
                ChallengersItem(
                    userName = userName,
                    imagePath = user?.imageFile?.path.toString()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "신고 사유 선택",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 16.dp),
                    color = DefaultBlack
                )
                if (reportList != null) {
                    LazyColumn(
                        modifier = Modifier,
                    ) {
                        items(reportList) { item ->
                            LabeledCircleCheck(
                                label = item.name,
                                checked = checked,
                                onClick = {
                                    checked = !checked
                                }
                            )
                        }
                    }
                }
            }

            FlatDoubleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                "신고하기",
                "취소",
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

