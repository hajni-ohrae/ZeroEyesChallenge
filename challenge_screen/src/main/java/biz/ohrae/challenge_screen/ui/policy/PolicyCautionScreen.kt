package biz.ohrae.challenge_screen.ui.policy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_component.R

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun PolicyCautionScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .background(DefaultWhite)
            .padding(24.dp, 0.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.statement_caution),
            contentDescription = "statement_caution",
            contentScale = ContentScale.FillWidth
        )
    }
}

private val QUALIFICATION = listOf(
    "제로아이즈(외 스터디카페 서비스)서비스 회원이면 누구나 참여 가능합니다",
    "목표 달성을 원하는 사람들에게 최적의 환경을 제공함으로써, 사람들의 목표달성률을 높인다는 목적 하에 제공되는 서비스이기에 회원이 특정 직업을 영위하는 등의 행위는 챌린지 참여나 챌린지 개설은 참가 자격과 개설자격에 제한을 둘수 있습니다",
    "참가 자격 제한요건에 해당됨에도 불구하고 특정 챌린지에 참가한 회원에게는 챌린지 종료후에도 해당 챌린지의 목표달성률과 관계없이 리워즈가 지급되지 않습니다. 단, 해당 회원의 챌린지 참가비는 챌린지 목표달성률에 따라 환급된다",
    "회사는 특정 챌린지의 참가조건 설정 오류 등으로 회사의 개설 의도대로 챌린지가 진행되기 어렵다고 판단하면, 회원의 챌린지 참가 신청을 취소할 수 있으며, 별도의 안내 등을 통해 참가 신청이 취소되었음을 회원에게 고지합니다",
)

private val JOIN_FEE = listOf(
    "제로아이즈(외 스터디카페 서비스)서비스 회원이면 누구나 참여 가능합니다",
    "목표 달성을 원하는 사람들에게 최적의 환경을 제공함으로써, 사람들의 목표달성률을 높인다는 목적 하에 제공되는 서비스이기에 회원이 특정 직업을 영위하는 등의 행위는 챌린지 참여나 챌린지 개설은 참가 자격과 개설자격에 제한을 둘수 있습니다",
    "참가 자격 제한요건에 해당됨에도 불구하고 특정 챌린지에 참가한 회원에게는 챌린지 종료후에도 해당 챌린지의 목표달성률과 관계없이 리워즈가 지급되지 않습니다. 단, 해당 회원의 챌린지 참가비는 챌린지 목표달성률에 따라 환급된다",
    "회사는 특정 챌린지의 참가조건 설정 오류 등으로 회사의 개설 의도대로 챌린지가 진행되기 어렵다고 판단하면, 회원의 챌린지 참가 신청을 취소할 수 있으며, 별도의 안내 등을 통해 참가 신청이 취소되었음을 회원에게 고지합니다",
)
