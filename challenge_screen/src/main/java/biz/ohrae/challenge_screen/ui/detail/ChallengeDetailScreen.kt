package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.Avatar
import biz.ohrae.challenge.ui.components.button.ArrowTextButton2
import biz.ohrae.challenge.ui.components.button.FlatBookMarkButton
import biz.ohrae.challenge.ui.components.card.RedCardInfo
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailFreeDescription
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailRefundDescription
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailsTitle
import biz.ohrae.challenge.ui.components.image.ImageBox
import biz.ohrae.challenge.ui.components.image.ImageBoxWithExampleTitle
import biz.ohrae.challenge.ui.components.text.MiddleDotText
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeDetailStatusMap
import biz.ohrae.challenge.util.challengeVerificationDayMap
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.Utils

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengeDetailScreen(
    challengeData: ChallengeData? = ChallengeData.mock(),
    challengers: List<User>? = null,
    clickListener: ChallengeDetailClickListener? = null,
    isParticipant: Boolean = false,
    viewModel: ChallengeDetailViewModel? = null
) {
    if (challengeData == null) {
        return
    }

    val scrollState = rememberScrollState()
    var status by remember { mutableStateOf(challengeDetailStatusMap[challengeData.status]) }
    LaunchedEffect(challengeData) {
        status = challengeDetailStatusMap[challengeData.status]
    }
    LaunchedEffect(isParticipant) {
        if (isParticipant) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }
    val bottomBtnName by remember {
        mutableStateOf(
            if (challengeData.is_cancelable == 1 || !challengeData.inChallenge.isNullOrEmpty()) {
                "참여 취소"
            } else {
                "참여 신청"
            }
        )
    }
    val checked = remember {
        mutableStateOf(challengeData.is_like == 1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(DefaultWhite)
    ) {
        ImageBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.51f)
                .background(TextBlack),
            imagePath = challengeData.imageFile?.path.toString(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            if (status != null) {
                val authMethod = Utils.getAuthMethodText(challengeData)
                val ageType = Utils.getAgeType(challengeData.age_limit_type.toString())
                ChallengeDetailsTitle(
                    status = status!!,
                    personnel = challengeData.summary?.total_user_cnt ?: 0,
                    detailTitle = challengeData.goal.toString(),
                    startDay = challengeData.start_date.toString(),
                    endDay = challengeData.end_date.toString(),
                    authMethod = authMethod,
                    ageLimitType = ageType
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xffebebeb))
            )
            Spacer(modifier = Modifier.height(32.dp))
            ChallengeDescription(
                challengeData = challengeData,
                clickListener = clickListener
            )
            if (challengeData.is_verification_photo == 1) {
                ChallengePhotoAuthentication()
            }
//            Divider(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .background(Color(0xffebebeb))
//            )
            ChallengeHost(challengeData = challengeData)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xffebebeb))
            )
            if (!challengers.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Challengers(
                    challengers = challengers,
                    clickListener = clickListener
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
            ArrowTextButton2(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3.71f),
                text = "챌린지 이용 주의사항",
                textColor = TextBlack,
                onClick = {
                    clickListener?.onClickCaution()
                }
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xffebebeb))
            )
            Spacer(modifier = Modifier.height(96.dp))
        }
        FlatBookMarkButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = bottomBtnName,
            onClick = { clickListener?.onClickParticipation() },
            onClickBookMark = {
                checked.value = !checked.value
                clickListener?.onClickBookMark(checked.value)
            },
            checked = checked.value
        )
    }
}

@Composable
fun ChallengeDescription(
    challengeData: ChallengeData,
    clickListener: ChallengeDetailClickListener? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (challengeData.min_deposit_amount > 0) {
            Text(
                text = "왜 돈을 걸어야 하나요?",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            val annotatedString = buildAnnotatedString {
                append(
                    "확실한 동기부여를 위해서 돈을 걸어요\n" +
                            "챌린지를 시작하기 전에 돈을 걸고 \n" +
                            "내가 실천한 만큼 돌려받으면 끝까지 포기할 수가 없죠\n"
                )
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                    append("돈을 걸기 전과 후 달라진 나의 변화를 경험")
                }
                append("해보세요")
            }
            Text(
                text = annotatedString,
                style = myTypography.default,
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 21.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            ChallengeDetailRefundDescription(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.88f)
            )
        } else {
            Text(
                text = "목표 달성률 및 상금",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "무료로 진행되는 챌린지는 목표 달성률과 리워즈가\n" +
                        "개설자가 정한 기준에 따라 달라질수 있습니다.",
                style = myTypography.default,
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 21.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            ChallengeDetailFreeDescription(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.90f),
                challengeData.achievement_percent.toInt(),
                challengeData.free_rewards,
                challengeData.free_rewards_winners,
                challengeData.free_rewards_offer_way
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "챌린지 진행시 꼭 확인하세요!",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val week by remember { mutableStateOf(challengeData.period) }
        val periodType by remember { mutableStateOf(challengeVerificationPeriodMap[challengeData.verification_period_type]) }
        val weekType = if (periodType.isNullOrEmpty()) "${challengeData.per_week}회" else periodType

        val topText = if (challengeData.is_verification_photo == 1) {
            val subText = if (weekType == "매일") {
                "하루에 한번 인증샷을 촬영하셔야 합니다."
            } else {
                "인증샷을 촬영하셔야 합니다."
            }

            "${week}주 동안 $weekType, $subText"
        } else if (challengeData.is_verification_time == 1) {
            "${week}주 동안 $weekType, 이용권 사용 내역이 이용시간으로 자동 인증됩니다."
        } else if (challengeData.is_verification_checkin == 1) {
            "${week}주 동안 $weekType, 출입한 내역이 출석으로  자동 인증됩니다"
        } else {
            ""
        }

        if (topText.isNotEmpty()) {
            MiddleDotText(
                text = topText,
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        var days by remember { mutableStateOf(challengeVerificationDayMap[challengeData.verification_period_type]) }
        if (days.isNullOrEmpty()) days = "월,화,수,목,금,토,일"
        MiddleDotText(
            text = "인증 가능한 요일은 $days 입니다",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (challengeData.is_verification_photo == 1) {
            MiddleDotText(
                text = "사진첩은 사용하실 수 없습니다",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiddleDotText(
                text = "인증샷 피드에 인증샷이 공개됩니다",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
        } else if (challengeData.is_verification_time == 1) {
            MiddleDotText(
                text = "이용시간은 이용권으로 입실한 시점부터 퇴실까지의 시간이 자동 누적됩니다 (외출시간 포함)",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiddleDotText(
                text = "인증샷 피드에 이용권 사용 시간이 공개됩니다",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
        } else if (challengeData.is_verification_checkin == 1) {
            MiddleDotText(
                text = "출석은 매일 00시를 기준으로 처음 입실한 시각이 자동 체크됩니다",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiddleDotText(
                text = "인증샷 피드에 이용권 입실 시간이 공개됩니다",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        RedCardInfo(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(13f),
            onClick = {
                clickListener?.onClickRedCardInfo()
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (!challengeData.caution.isNullOrEmpty()) {
            Text(
                text = "인증 방법 및 주의사항",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = challengeData.caution.toString(),
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
)
@Composable
fun ChallengePhotoAuthentication() {
    Column(modifier = Modifier.fillMaxWidth()) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val itemHeight = screenWidth.value / 1.40625f

        Text(
            text = "인증샷 이렇게 찍어주세요",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ImageBoxWithExampleTitle(
                    modifier = Modifier.size(itemHeight.dp),
                    resourceId = biz.ohrae.challenge_component.R.drawable.img_good_example,
                    isGood = true
                )
            }
            item {
                ImageBoxWithExampleTitle(
                    modifier = Modifier.size(itemHeight.dp),
                    resourceId = biz.ohrae.challenge_component.R.drawable.img_bad_example,
                    isGood = false
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ChallengeHost(challengeData: ChallengeData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "챌린지 호스트",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = challengeData.owner?.getUserName().toString(),
                style = myTypography.extraBold,
                fontSize = dpToSp(dp = 20.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}