package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.register.ChallengeOpenState
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.calender.ChallengeCalendarCard
import biz.ohrae.challenge.ui.components.card.ChallengeStartEndDateCard
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import kotlinx.coroutines.selects.select
import java.text.SimpleDateFormat
import java.util.*


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ChallengeOpenScreen(
    challengeOpenState: ChallengeOpenState = ChallengeOpenState.mock(),
    clickListener: RegisterClickListener? = null,
    challengeAuth: Int? = 0,
    viewModel: ChallengeRegisterViewModel? = null
) {
    val nowTime = System.currentTimeMillis()
    val tDate = Date(nowTime)
    val tDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))
    val calendarText = SimpleDateFormat("MM월dd일 (E)", Locale("ko", "KR"))
    val today = calendarText.format(tDate)

    val day = tDateFormat.format(tDate)


    Column(
        modifier = Modifier
            .background(DefaultWhite)
            .padding(24.dp, 0.dp)
    ) {
        Text(
            text = "챌린저 모집",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 20.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "인증은 24시간(1일) 이내에 한번만 가능합니다",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff005bad)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "챌린지 시작일",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "인증을 시작할 날짜를 선택하세요",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 14.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChallengeCalendarCard(today)
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "인증 빈도",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "인증할 주기와 횟수를 선택하세요",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 14.dp),
            color = DefaultBlack
        )
        DropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "",
            list = challengeOpenState.authCycleList,
            viewModel = viewModel
        )

        DropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "",
            list = challengeOpenState.authFrequencyList,
            viewModel = viewModel
        )

        if (challengeAuth == 1) {
            MyDropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7.1f),
                label = "",
                list = challengeOpenState.authFrequencyList,
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        ChallengeStartEndDateCard("챌린지 종료일")
    }
    Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {

        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "다음",
            onClick = { clickListener?.onClickOpenNext(day, "1주동안", "매일인증") }
        )
    }


}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    label: String,
    list: List<String>,
    viewModel: ChallengeRegisterViewModel?
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(list[0]) }
    var cardWidth by remember { mutableStateOf(Size.Zero) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = myTypography.default,
            fontSize = dpToSp(dp = 10.dp),
            color = Color(0xff6c6c6c)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = modifier.onSizeChanged {
                cardWidth = Size(it.width.toFloat(), it.height.toFloat())
            },
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(1.dp, Color(0xff959595)),
            elevation = 0.dp,
            onClick = {
                expanded = true
            },
            backgroundColor = DefaultWhite
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedItem,
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color(0xff6c6c6c)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_slidedown),
                        contentDescription = "arrow_slidedown",
                        tint = Color.Unspecified
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { cardWidth.width.toDp() })
                        .background(
                            DefaultWhite
                        ),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    list.forEach {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                selectedItem = it
                                viewModel?.selectDropBox(it)
                            }
                        ) {
                            Text(
                                text = it,
                                style = myTypography.bold,
                                fontSize = dpToSp(dp = 16.dp),
                                color = Color(0xff6c6c6c)
                            )
                        }
                    }
                }
            }
        }
    }
}