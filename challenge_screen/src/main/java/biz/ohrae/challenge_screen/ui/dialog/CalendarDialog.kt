package biz.ohrae.challenge_screen.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.window.layout.WindowMetricsCalculator
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.GrayColor6
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.ui.register.ChallengeRegisterViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class CalendarDialog(private val challengeRegisterViewModel: ChallengeRegisterViewModel) :
    DialogFragment() {
    private lateinit var calendarDialogListener: CalendarDialogListener


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
                Calendar(
                    listener = calendarDialogListener,
                    "선택",
                    "취소",
                    viewModel = challengeRegisterViewModel
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

    fun setListener(listener: CalendarDialogListener) {
        this.calendarDialogListener = listener
    }
}

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
fun Calendar(
    listener: CalendarDialogListener? = null,
    positiveBtnName: String = "",
    negativeBtnName: String = "",
    viewModel: ChallengeRegisterViewModel? = null
) {
    var enabled by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = DefaultWhite
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f)
            ) {
                ComposeCalendar(
                    onDayClick = { day ->
                        if (Utils.isAfter(day.date.toString())) {
                            enabled = true
                            listener?.clickDay(day.date.toString())
                        } else {
                            enabled = false
                        }
                    }
                )
            }
            FlatDoubleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                rightText = positiveBtnName,
                leftText = negativeBtnName,
                enabled = enabled,
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

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun ComposeCalendar(
    startMonth: YearMonth = YearMonth.now(),
    endMonth: YearMonth = startMonth.plusMonths(3),
    onDayClick: (day: CalendarDay) -> Unit = {}
) {
    val now by remember { mutableStateOf(LocalDate.now()) }
    val coroutineScope = rememberCoroutineScope()
    val selections = remember { mutableStateListOf<CalendarDay>() }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
    )
    val daysOfWeek = remember { daysOfWeek() }
    var visibleMonth by remember { mutableStateOf(YearMonth.now()) }

    LaunchedEffect(state.firstVisibleMonth) {
        Timber.e("state : ${state.firstVisibleMonth.toString()}")
        visibleMonth = state.firstVisibleMonth.yearMonth
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DefaultWhite)
    ) {
        CalendarTitle(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth = visibleMonth,
            goToPrevious = {
                coroutineScope.launch {
                    if (visibleMonth.isAfter(startMonth)) {
                        visibleMonth = visibleMonth.minusMonths(1)
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                    }
                }
            },
            goToNext = {
                coroutineScope.launch {
                    if (visibleMonth.isBefore(endMonth)) {
                        visibleMonth = visibleMonth.plusMonths(1)
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                    }
                }
            }
        )
        HorizontalCalendar(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    isSelected = selections.contains(day),
                    isAfter = day.date.isAfter(now),
                ) { clicked ->
                    selections.clear()
                    selections.add(clicked)
                    onDayClick(clicked)
                }
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            },
        )
    }
}

@Composable
private fun CalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(
            onClick = {
                goToPrevious()
            }
        ) {
            Text(
                text = "이전",
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff005bad)
            )
        }
        Text(
            modifier = Modifier
                .weight(1f),
            text = currentMonth.toString(),
            fontSize = dpToSp(dp = 22.dp),
            textAlign = TextAlign.Center,
            style = myTypography.bold
        )
        TextButton(
            onClick = {
                goToNext()
            }
        ) {
            Text(
                text = "다음",
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff005bad)
            )
        }
    }
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = dpToSp(dp = 16.dp),
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA),
                style = myTypography.bold,
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isAfter: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Color(0xff005bad) else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = {
                    if (day.date.isAfter(LocalDate.now())) {
                        onClick(day)
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        val textColor = when (day.position) {
            // Color.Unspecified will use the default text color from the current theme
            DayPosition.MonthDate -> {
                if (isSelected) {
                    Color.White
                } else {
                    if (isAfter) {
                        Color.Unspecified
                    } else {
                        GrayColor6
                    }
                }
            }
            DayPosition.InDate, DayPosition.OutDate -> GrayColor6
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = dpToSp(dp = 14.dp),
        )
    }
}


