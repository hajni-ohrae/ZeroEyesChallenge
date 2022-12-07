package biz.ohrae.challenge_screen.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.components.checkBox.MyCheckBox
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_screen.model.main.FilterState
import biz.ohrae.challenge_screen.ui.main.ChallengeMainViewModel

class FilterDialog(private val viewModel: ChallengeMainViewModel) : DialogFragment() {
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
                val filterState by viewModel.filterState.observeAsState()

                Filter(
                    listener = filterDialogListener,
                    "초기화",
                    "100개 챌린지 보기",
                    filterState = filterState!!
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
    filterState: FilterState = FilterState.mock()
) {
    var checked by remember { mutableStateOf(false) }
    var periodType: String
    var perWeek: String
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
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "필터",
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 20.dp),
                        color = DefaultBlack
                    )
                    TextButton(onClick = { listener?.clickInitialization() }) {
                        Text(
                            text = "취소",
                            color = Color(0xff747474),
                            style = myTypography.w500,
                            fontSize = dpToSp(dp = 15.dp),
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .background(DefaultWhite)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 19.dp),
                        text = "인증빈도",
                        fontSize = dpToSp(dp = 15.dp)
                    )
                    LazyVerticalGrid(
                        userScrollEnabled = false,
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(17.dp),
                        horizontalArrangement = Arrangement.spacedBy(3.5.dp),
                    ) {
                        items(filterState.certifiedList) {
                            MyCheckBox(
                                checkBoxSize = 20.dp,
                                label = it.name,
                                labelStyle = myTypography.w700,
                                onClick = {
                                    checked = !checked
                                    listener?.selectVerificationPeriodType(it.name_en)
                                },
                                onChecked = {
                                    checked = !checked
                                    listener?.selectVerificationPeriodType(it.name_en)
                                },
                                checked = it.name_en == filterState.selectVerificationPeriodType,
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .background(DefaultWhite)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 19.dp),
                        text = "챌린지 기간",
                        fontSize = dpToSp(dp = 15.dp)
                    )
                    LazyVerticalGrid(
                        userScrollEnabled = false,
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(17.dp),
                        horizontalArrangement = Arrangement.spacedBy(3.5.dp),
                    ) {
                        items(filterState.periodList) {
                            MyCheckBox(
                                checkBoxSize = 20.dp,
                                label = it.name,
                                labelStyle = myTypography.w700,
                                onClick = {
                                    checked = !checked
                                    listener?.selectPeriod(it.name_en)
                                },
                                onChecked = {
                                    checked = !checked
                                    listener?.selectPeriod(it.name_en)
                                },
                                checked = it.name_en == filterState.selectPeriod,
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .background(DefaultWhite)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 19.dp),
                        text = "기타",
                        fontSize = dpToSp(dp = 15.dp)
                    )
                    LazyVerticalGrid(
                        userScrollEnabled = false,
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(17.dp),
                        horizontalArrangement = Arrangement.spacedBy(3.5.dp),
                    ) {
                        items(filterState.etcList) {
                            MyCheckBox(
                                checkBoxSize = 20.dp,
                                label = it.name,
                                labelStyle = myTypography.w700,
                                onClick = {
                                    checked = !checked
                                    listener?.selectIsAdultOnly(it.name_en)
                                },
                                onChecked = {
                                    checked = !checked
                                    listener?.selectIsAdultOnly(it.name_en)
                                },
                                checked = it.name_en == filterState.selectIsAdultOnly,
                            )
                        }
                    }
                }
            }

            if (filterState.selectVerificationPeriodType.length == 1) {
                periodType = "per_week"
                perWeek = filterState.selectVerificationPeriodType
            } else {
                periodType = filterState.selectVerificationPeriodType
                perWeek = ""
            }

            FlatDoubleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                "챌린지 보기",
                "초기화",
                onClickRight = {
                    listener?.clickPositive(
                        periodType,
                        perWeek,
                        filterState.selectPeriod,
                        filterState.selectIsAdultOnly
                    )
                },
                onClickLeft = {
                    listener?.selectPeriod("")
                    listener?.selectVerificationPeriodType("")
                    listener?.selectIsAdultOnly("")
                }
            )
        }
    }
}

