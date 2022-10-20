package biz.ohrae.challenge_screen.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.window.layout.WindowMetricsCalculator
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.ui.register.ChallengeRegisterViewModel
import com.himanshoe.kalendar.common.KalendarKonfig
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.YearRange
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import timber.log.Timber
import java.time.DayOfWeek
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

    fun setListener(listener: CalendarDialogListener) {
        this.calendarDialogListener = listener
    }
}

@Preview(
    widthDp = 800,
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
        shape = RectangleShape,
        backgroundColor = DefaultWhite
    ) {
        Column() {
            Kalendar(
                kalendarType = KalendarType.Firey(),
                kalendarKonfig = KalendarKonfig(
                    yearRange = YearRange(2022, 2023),
                    weekCharacters = 1, locale = Locale.KOREA
                ),
                onCurrentDayClick = { day, event ->
                    Timber.d("day : $day")
                    if (Utils.isAfter(day.toString())) {
                        enabled = true
                        listener?.clickDay(day.toString())
                    } else {
                        enabled = false
                    }
                },
                errorMessage = {
                    //Handle the error if any
                },
                kalendarStyle = KalendarStyle(
                    kalendarSelector = KalendarSelector.Circle(
                        selectedColor = Color(0xff005bad),
                        todayColor = Color(0x26005bad)
                    ),
                    kalendarBackgroundColor = DefaultWhite,
                    elevation = 0.dp,
                ),
            )
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
private fun ComposeCalendar() {
    val selections = remember { mutableStateListOf<CalendarDay>() }
    val state = rememberCalendarState(
        startMonth = YearMonth.now(),
        endMonth = YearMonth.of(2202, 12),
    )
    val daysOfWeek = remember { daysOfWeek() }

    HorizontalCalendar(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        dayContent = { day ->
            Day(day, isSelected = selections.contains(day)) { clicked ->
                if (selections.contains(clicked)) {
                    selections.remove(clicked)
                } else {
                    selections.add(clicked)
                }
            }
        },
        monthHeader = {
            MonthHeader(daysOfWeek = daysOfWeek)
        }
    )
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = dpToSp(dp = 16.dp),
                text = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREA),
                style = myTypography.bold,
            )
        }
    }
}

@Composable
private fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Color(0xff005bad) else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        val textColor = when (day.position) {
            // Color.Unspecified will use the default text color from the current theme
            DayPosition.MonthDate -> if (isSelected) Color.White else Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> Color(0xff6c6c6c)
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = dpToSp(dp = 14.dp),
        )
    }
}


