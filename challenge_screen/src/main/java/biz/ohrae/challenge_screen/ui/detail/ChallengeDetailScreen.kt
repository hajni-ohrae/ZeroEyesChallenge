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
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
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
import timber.log.Timber

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
    viewModel: ChallengeDetailViewModel? = null,
    userData: User? = null,
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(DefaultWhite)
    ) {
        ImageBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(238.dp)
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
                    totalUserCount = challengeData.summary?.total_user_cnt ?: 0,
                    userData = userData,
                    authType = Utils.getAuthTypeEnglish(challengeData),
                    clickListener = clickListener
                )
                Spacer(modifier = Modifier.height(32.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xffebebeb))
                )
            }
            ArrowTextButton2(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp),
                text = "????????? ?????? ????????????",
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
    }

    Box(modifier = Modifier .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        BookmarkButton(
            challengeData = challengeData,
            clickListener = clickListener
        )
    }
}

@Composable
private fun BookmarkButton(
    challengeData: ChallengeData,
    clickListener: ChallengeDetailClickListener?,
) {
    val checked = remember {
        mutableStateOf(challengeData.is_like == 1)
    }

    val bottomBtnName by remember {
        mutableStateOf(
            if (challengeData.is_cancelable == 1 || !challengeData.inChallenge.isNullOrEmpty()) {
                "?????? ??????"
            } else {
                "?????? ??????"
            }
        )
    }

    FlatBookMarkButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        text = bottomBtnName,
        onClick = { clickListener?.onClickParticipation() },
        onClickBookMark = {
            checked.value = !checked.value
            clickListener?.onClickBookMark(checked.value)
        },
        checked = checked.value
    )
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
                text = "??? ???????????? ????????? ??????????",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            val annotatedString = buildAnnotatedString {
                append(
                    "????????? ??????????????? ????????? ???????????? ?????????\n" +
                            "???????????? ???????????? ?????? ???????????? ?????? \n" +
                            "?????? ????????? ?????? ??????????????? ????????? ????????? ?????? ??????\n"
                )
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                    append("???????????? ?????? ?????? ??? ????????? ?????? ????????? ??????")
                }
                append("????????????")
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
                    .height(164.dp)
            )
        } else {
            Text(
                text = "?????? ????????? ??? ?????????",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "????????? ???????????? ???????????? ?????? ???????????? ????????????\n" +
                        "???????????? ?????? ????????? ?????? ???????????? ????????????.",
                style = myTypography.default,
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 21.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            ChallengeDetailFreeDescription(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(164.dp),
                challengeData.achievement_percent.toInt(),
                challengeData.free_rewards,
                challengeData.free_rewards_winners,
                challengeData.free_rewards_offer_way
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "????????? ????????? ??? ???????????????!",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val week by remember { mutableStateOf(challengeData.period) }
        val time by remember { mutableStateOf(challengeData.verification_daily_staying_time) }
        val periodType by remember { mutableStateOf(challengeVerificationPeriodMap[challengeData.verification_period_type]) }
        val weekType = if (periodType.isNullOrEmpty()) "${challengeData.per_week}???" else periodType

        val topText = if (challengeData.is_verification_photo == 1) {
            val subText = if (weekType == "??????") {
                "????????? ?????? ???????????? ??????????????? ?????????."
            } else {
                "???????????? ??????????????? ?????????."
            }

            "${week}??? ?????? $weekType, $subText"
        } else if (challengeData.is_verification_time == 1) {
            "${week}??? ?????? $weekType, 1??? ?????? ${time}???????????? ????????? ?????? ????????? ?????????????????? ?????? ???????????????."
        } else if (challengeData.is_verification_checkin == 1) {
            "${week}??? ?????? $weekType, ????????? ????????? ????????????  ?????? ???????????????"
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
        if (days.isNullOrEmpty()) days = "???,???,???,???,???,???,???"
        MiddleDotText(
            text = "?????? ????????? ????????? $days ?????????",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (challengeData.is_verification_photo == 1) {
            MiddleDotText(
                text = "???????????? ???????????? ??? ????????????",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiddleDotText(
                text = "????????? ????????? ???????????? ???????????????",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
        } else if (challengeData.is_verification_time == 1) {
            MiddleDotText(
                text = "??????????????? ??????????????? ????????? ???????????? ??????????????? ????????? ?????? ??????????????? (???????????? ??????)",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiddleDotText(
                text = "????????? ????????? ????????? ?????? ????????? ???????????????",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
        } else if (challengeData.is_verification_checkin == 1) {
            MiddleDotText(
                text = "????????? ?????? 00?????? ???????????? ?????? ????????? ????????? ?????? ???????????????",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiddleDotText(
                text = "????????? ????????? ????????? ?????? ????????? ???????????????",
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 19.6.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        RedCardInfo(
            modifier = Modifier
                .fillMaxWidth()
                .height(33.dp),
            onClick = {
                clickListener?.onClickRedCardInfo()
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (!challengeData.caution.isNullOrEmpty()) {
            Text(
                text = "?????? ?????? ??? ????????????",
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
            text = "????????? ????????? ???????????????",
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
                    modifier = Modifier.size(240.dp),
                    resourceId = biz.ohrae.challenge_component.R.drawable.img_good_example,
                    isGood = true
                )
            }
            item {
                ImageBoxWithExampleTitle(
                    modifier = Modifier.size(240.dp),
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
            text = "????????? ?????????",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Timber.e("challengeData.owner?.imageFile?.path : ${challengeData.owner?.imageFile?.path}")
            circularAvatar(
                url = challengeData.owner?.imageFile?.path ?: "",
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