package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBookMarkButton
import biz.ohrae.challenge.ui.components.button.FlatBorderButton
import biz.ohrae.challenge.ui.components.card.RedCardInfo
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailFreeDescription
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailRefundDescription
import biz.ohrae.challenge.ui.components.detail.ChallengeJoinedDetailsTitle
import biz.ohrae.challenge.ui.components.image.ImageBox
import biz.ohrae.challenge.ui.components.list_item.ChallengersItem
import biz.ohrae.challenge.ui.components.list_item.ProgressRatioItem
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
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengeJoinedDetailScreen(
    challengeData: ChallengeData? = ChallengeData.mock(),
    challengers: List<User>? = null,
    clickListener: ChallengeDetailClickListener? = null
) {
    if (challengeData == null) {
        return
    }

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf(challengeDetailStatusMap[challengeData.status]) }
    LaunchedEffect(challengeData) {
        status = challengeDetailStatusMap[challengeData.status]
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(24.dp, 0.dp)
        ) {
            item {
                Column {
                    ImageBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.51f),
                        imagePath = ""
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            if (status != null) {
                item {
                    ColumnForLazy {
                        ChallengeJoinedDetailsTitle(
                            status = status!!,
                            personnel = 0,
                            detailTitle = challengeData.goal.toString(),
                            isFree = challengeData.min_deposit_amount == 0,
                            isAdult = challengeData.is_adult_only == 1,
                            isPhoto = challengeData.is_verification_photo == 1,
                            startDay = challengeData.start_date.toString(),
                            endDay = challengeData.end_date.toString()
                        )
                    }
                }
            }
            item {
                ColumnForLazy {
                    Spacer(modifier = Modifier.height(24.dp))
                    TabRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(6.42f),
                        selectedTabIndex = pagerState.currentPage,
                        backgroundColor = DefaultWhite,
                        indicator = {
                            TabRowDefaults.Indicator(
                                modifier = Modifier.tabIndicatorOffset(it[pagerState.currentPage]),
                                color = Color(0xff005bad),
                                height = 2.dp
                            )
                        }
                    ) {
                        Tab(
                            selected = pagerState.currentPage == 0,
                            selectedContentColor = Color(0xff005bad),
                            unselectedContentColor = Color(0xffc7c7c7),
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(0)
                                }
                            },
                        ) {
                            Text(
                                text = "상세보기",
                                fontSize = dpToSp(dp = 16.dp),
                                style = myTypography.bold
                            )
                        }
                        Tab(
                            selected = pagerState.currentPage == 1,
                            selectedContentColor = Color(0xff005bad),
                            unselectedContentColor = Color(0xffc7c7c7),
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(1)
                                }
                            },
                        ) {
                            Text(
                                text = "인증",
                                fontSize = dpToSp(dp = 16.dp),
                                style = myTypography.bold
                            )
                        }
                    }
                }
            }
            item {
                ColumnForLazy {
                    HorizontalPager(
                        count = 2, state = pagerState
                    ) { page ->
                        if (page == 0) {
                            ChallengeJoinedDetailPage(
                                challengeData = challengeData,
                                challengers = challengers,
                                clickListener = clickListener
                            )
                        } else {
                            ChallengeAuthPage(
                                challengeData = challengeData,
                                clickListener = clickListener
                            )
                        }
                    }
                }
            }
            item {
                FlatBookMarkButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(6f),
                    text = "인증하기",
                    onClick = { clickListener?.onClickAuth() }
                )
            }
        }
    }
}

@Composable
private fun ChallengeProgressDetail(
    successCount: Int = 0,
    remainCount: Int = 0,
    failCount: Int = 0
) {
    val list = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
    )
    val columnCount by remember { mutableStateOf((list.size / 10) + (list.size % 10)) }

    Column {
        Text(
            text = "달성률",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = "인증성공 ${successCount}개",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "남은인증 ${remainCount}개",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "인증실패 ${failCount}개",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        val spacing by remember { mutableStateOf(3.5.dp) }
        val itemSize = (LocalConfiguration.current.screenWidthDp.dp - 48.dp - (spacing * 9)) / 10

        FlowRow(
            mainAxisSize = SizeMode.Expand,
            crossAxisAlignment = FlowCrossAxisAlignment.Start,
            mainAxisSpacing = 3.5.dp,
            crossAxisSpacing = 3.5.dp
        ) {
            list.forEachIndexed { index, item ->
                ProgressRatioItem(
                    modifier = Modifier.size(itemSize),
                    isSuccess = false,
                    number = item
                )
            }
        }

//        for (i in 0 until columnCount) {
//            val start = i * 10
//            val end = if (start + 10 > list.size) {
//                list.size
//            } else {
//                start + 10
//            }
//            val sublist = list.subList(start, end)
//            Timber.e("start :$i, end :$end")
//            Column(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    for (item in sublist) {
//                        ProgressRatioItem(modifier = Modifier.weight(1f), isSuccess = false, number = item)
//                        Spacer(modifier = Modifier.width(4.dp))
//                    }
//                }
//                Spacer(modifier = Modifier.height(4.dp))
//            }
//        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb))
        )
    }
}

@Composable
private fun ChallengeJoinedDetailPage(
    challengeData: ChallengeData,
    challengers: List<User>?,
    clickListener: ChallengeDetailClickListener? = null
) {
    Column {
        Spacer(modifier = Modifier.height(32.dp))
        ChallengeProgressDetail(
            successCount = 10,
            remainCount = 1,
            failCount = 1
        )
        Spacer(modifier = Modifier.height(24.dp))
        ChallengeJoinedDescription(
            challengeData = challengeData,
            clickListener = clickListener
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb))
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (challengers != null) {
            Challengers(challengers)
        }
    }
}

@Composable
private fun ChallengeJoinedDescription(
    challengeData: ChallengeData,
    clickListener: ChallengeDetailClickListener? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "챌린지 진행시 꼭 확인하세요!",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val week by remember { mutableStateOf(challengeData.period) }
        val periodType by remember { mutableStateOf(challengeVerificationPeriodMap[challengeData.verification_period_type]) }
        MiddleDotText(
            text = "${week}주 동안 $periodType, 이용권 사용 내역이 이용시간으로 자동 인증됩니다.",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        val days by remember { mutableStateOf(challengeVerificationDayMap[challengeData.verification_period_type]) }
        MiddleDotText(
            text = "인증 가능한 요일은 $days 입니다",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
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
        if (challengeData.is_verification_photo == 1) {
            Spacer(modifier = Modifier.height(8.dp))
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
        Text(
            text = "인증 방법 및 주의사항",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(13.dp))
        MiddleDotText(
            text = "만화책은 안됩니다.",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        MiddleDotText(
            text = "손은 어떤 제스처든 모두 가능해요.",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (challengeData.is_verification_photo == 1) {
            ChallengePhotoAuthentication()
        }
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
                    .aspectRatio(1.90f)
            )
        }
    }
}

@Composable
private fun ColumnForLazy(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp),
    ) {
        content()
    }
}

@Composable
private fun Challengers(challengers: List<User>) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xff4985f8))) {
                    append("${challengers.size}명 ")
                }
                append("참여")
            }
            Text(
                text = "챌린저스",
                color = TextBlack,
                fontSize = dpToSp(dp = 16.dp),
                style = myTypography.bold
            )
            Text(
                text = annotatedString,
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.default
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        challengers.forEachIndexed { index, user ->
            if (index < 10) {
                val name = if (user.nickname == null) {
                    user.name
                } else {
                    user.nickname
                }
                ChallengersItem(
                    userName = name.toString(),
                    imagePath = user.image_path.toString()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (challengers.size > 10) {
            Spacer(modifier = Modifier.height(17.dp))
            FlatBorderButton(
                modifier = Modifier.fillMaxWidth().aspectRatio(7.1f),
                text = "전체보기",
                onClick = {}
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun ChallengeAuthPage(
    challengeData: ChallengeData,
    clickListener: ChallengeDetailClickListener? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "인증 내역이 없습니다",
            color = TextBlack,
            fontSize = dpToSp(dp = 14.dp),
            style = myTypography.bold
        )
    }
}
