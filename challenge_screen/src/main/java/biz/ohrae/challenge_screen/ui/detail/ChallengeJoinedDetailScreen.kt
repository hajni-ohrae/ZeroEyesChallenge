package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBookMarkButton
import biz.ohrae.challenge.ui.components.button.FlatBorderButton
import biz.ohrae.challenge.ui.components.card.CertificationImageItem
import biz.ohrae.challenge.ui.components.card.RedCardInfo
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailFreeDescription
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailRefundDescription
import biz.ohrae.challenge.ui.components.detail.ChallengeJoinedDetailsTitle
import biz.ohrae.challenge.ui.components.image.ImageBox
import biz.ohrae.challenge.ui.components.list_item.ChallengersItem
import biz.ohrae.challenge.ui.components.list_item.ProgressRatioFailItem
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
import biz.ohrae.challenge_repo.model.detail.Verification
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.detail.VerificationState
import biz.ohrae.challenge_screen.util.OnBottomReached
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengeJoinedDetailScreen(
    challengeData: ChallengeData? = ChallengeData.mock(),
    challengers: List<User>? = null,
    userData: User? = null,
    verificationState: VerificationState? = null,
    challengeVerifiedList: List<VerifyData>? = null,
    clickListener: ChallengeDetailClickListener? = null,
    onBottomReached: () -> Unit = {}
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
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = listState
        ) {
            item {
                Column {
                    ImageBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(238.dp)
                            .background(TextBlack),
                        imagePath = challengeData.imageFile?.path.toString(),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            if (status != null) {
                item {
                    ColumnForLazy(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 0.dp)
                    ) {
                        val authMethod = Utils.getAuthMethodText(challengeData)
                        val ageType = Utils.getAgeType(challengeData.age_limit_type.toString())

                        ChallengeJoinedDetailsTitle(
                            status = status!!,
                            personnel = challengeData.summary?.total_user_cnt ?: 0,
                            detailTitle = challengeData.goal.toString(),
                            isFree = challengeData.min_deposit_amount == 0,
                            ageType = ageType,
                            isPhoto = challengeData.is_verification_photo == 1,
                            isTime = challengeData.is_verification_time == 1,
                            isCheckIn = challengeData.is_verification_checkin == 1,
                            startDay = challengeData.start_date.toString(),
                            endDay = challengeData.end_date.toString(),
                            authMethod = authMethod
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
            stickyHeader {
                ColumnForLazy {
                    TabRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
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
            items(1) {
                ColumnForLazy(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 0.dp)
                ) {
                    var pagerHeight by remember { mutableStateOf(0.dp) }
                    var pagerModifier: Modifier by remember { mutableStateOf(Modifier) }

                    LaunchedEffect(pagerState.currentPage) {
                        pagerModifier = if (pagerState.currentPage == 0) {
                            if (pagerHeight > 0.dp) {
                                Modifier
                                    .fillMaxWidth()
                                    .height(pagerHeight)
                            } else {
                                Modifier.fillMaxWidth()
                            }
                        } else {
                            Modifier.fillMaxWidth()
                        }
                    }

                    HorizontalPager(
                        modifier = pagerModifier,
                        count = 2,
                        state = pagerState
                    ) { page ->
                        if (page == 0) {
                            val localDensity = LocalDensity.current
                            ChallengeJoinedDetailPage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .onGloballyPositioned { coordinates ->
                                        pagerHeight =
                                            with(localDensity) { coordinates.size.height.toDp() }
                                        Timber.e("pagerHeight : $pagerHeight")
                                    },
                                challengeData = challengeData,
                                challengers = challengers,
                                userData = userData,
                                verificationState = verificationState,
                                clickListener = clickListener
                            )
                        } else {
                            ChallengeAuthPage(
                                challengeVerifiedList = challengeVerifiedList,
                                clickListener = clickListener
                            )
                        }
                    }
                }
            }
            item {
                listState.OnBottomReached {
                    if (pagerState.currentPage == 1) {
                        Timber.e("ChallengeJoinedDetailScreen bottom reached!!")
                        onBottomReached()
                    }
                }
            }
        }
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
    val buttonName = Utils.getAuthButtonName(challengeData)
    val enabled = challengeData.is_verification_photo == 1 && !challengeData.isAuthed() && challengeData.achievement_percent.toDouble() < 100.0f

    FlatBookMarkButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        enabled = enabled,
        text = buttonName,
        onClick = { clickListener?.onClickAuth() },
        onClickBookMark = {
            checked.value = !checked.value
            clickListener?.onClickBookMark(checked.value)
        },
        checked = checked.value
    )
}

@Composable
private fun ChallengeProgressDetail(
    verificationState: VerificationState
) {
    Column {
        Row {
            Text(
                text = "달성률 ",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${verificationState.achievement}%",
                style = myTypography.bold,
                color = Color(0xff4985f8),
                fontSize = dpToSp(dp = 18.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = "인증성공 ",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Text(
                text = "${verificationState.successCount}",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff4985f8)
            )
            Text(
                text = "개",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "남은인증 ",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Text(
                text = "${verificationState.remainCount}",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff4985f8)
            )
            Text(
                text = "개",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "인증실패 ",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Text(
                text = "${verificationState.failCount}",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff4985f8)
            )
            Text(
                text = "개",
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
            verificationState.verifications?.forEach { item ->
                when (item.status) {
                    Verification.REMAINING, Verification.SUCCESS -> {
                        ProgressRatioItem(
                            modifier = Modifier.size(itemSize),
                            isSuccess = item.status == Verification.SUCCESS,
                            number = (item.day + 1).toString()
                        )
                    }
                    Verification.FAILURE -> {
                        ProgressRatioFailItem(
                            modifier = Modifier.size(itemSize),
                        )
                    }
                }
            }
        }
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
    modifier: Modifier = Modifier,
    challengeData: ChallengeData,
    challengers: List<User>?,
    userData: User? = null,
    verificationState: VerificationState? = null,
    clickListener: ChallengeDetailClickListener? = null
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(32.dp))
        if (verificationState != null) {
            ChallengeProgressDetail(verificationState = verificationState)
        }
        Spacer(modifier = Modifier.height(24.dp))
        ChallengeJoinedDescription(
            challengeData = challengeData,
            clickListener = clickListener
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (challengers != null) {
            Challengers(
                challengers = challengers,
                totalUserCount = challengeData.summary?.total_user_cnt ?: 0,
                userData = userData,
                clickListener = clickListener
            )
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
        val time by remember { mutableStateOf(challengeData.verification_daily_staying_time) }
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
            "${week}주 동안 $weekType, 1일 누적 ${time}시간이상 이용권 사용 내역이 이용시간으로 자동 인증됩니다."
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
        Spacer(modifier = Modifier.height(16.dp))
        RedCardInfo(
            modifier = Modifier
                .fillMaxWidth()
                .height(33.dp),
            onClick = {
                clickListener?.onClickRedCardInfo()
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb))
        )
        Spacer(modifier = Modifier.height(24.dp))
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
        if (challengeData.is_verification_photo == 1) {
            ChallengePhotoAuthentication()
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xffebebeb))
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        if (challengeData.min_deposit_amount > 0) {
            Text(
                text = "왜 참여금을 걸어야 하나요?",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            val annotatedString = buildAnnotatedString {
                append(
                    "확실한 동기부여를 위해서 참여금을 걸어요\n" +
                            "챌린지를 시작하기 전에 참여금을 걸고 \n" +
                            "내가 실천한 만큼 돌려받으면 끝까지 포기할 수가 없죠\n"
                )
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                    append("참여금을 걸기 전과 후 달라진 나의 변화를 경험")
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
                    .height(164.dp),
            )
        } else {
            Text(
                text = "목표 달성률 및 리워즈",
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
                    .height(164.dp),
                challengeData.achievement_percent.toInt(),
                challengeData.free_rewards,
                challengeData.free_rewards_winners,
                challengeData.free_rewards_offer_way
            )
        }
    }
}

@Composable
fun ColumnForLazy(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun Challengers(
    challengers: List<User>,
    totalUserCount: Int,
    userData: User? = null,
    clickListener: ChallengeDetailClickListener? = null
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff4985f8)
                    )
                ) {
                    append("${totalUserCount}명 ")
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
                ChallengersItem(
                    userName = user.getUserName(),
                    imagePath = user.imageFile?.path,
                    isMe = userData?.id == user.id
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
//        if (totalUserCount > 10) {
            Spacer(modifier = Modifier.height(17.dp))
            FlatBorderButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7.1f),
                text = "전체보기",
                onClick = {
                    clickListener?.onClickShowAllChallengers("")
                }
            )
//        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ChallengeAuthPage(
    challengeVerifiedList: List<VerifyData>? = null,
    clickListener: ChallengeDetailClickListener? = null
) {
    if (challengeVerifiedList.isNullOrEmpty()) {
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
    } else {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(33.dp))
            val column = 2
            val rows = if (challengeVerifiedList.isEmpty()) 0 else 1 + (challengeVerifiedList.count() - 1) / column
            for (i in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    for (columnIndex in 0 until column) {
                        val itemIndex = i * column + columnIndex
                        if (itemIndex < challengeVerifiedList.count()) {
                            val item = challengeVerifiedList[itemIndex]
                            val cnt = if (item.type == "staying_time") item.staying_time_cnt else item.cnt
                            val time =
                                when (item.type) {
                                    "staying_time" -> Utils.convertDate2(item.verified_date)
                                    "checkin" -> Utils.convertDate(item.checkin_date)
                                    else -> Utils.convertDate9(item.created_date, true)
                                }

                            CertificationImageItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                imageUrl = item.imageFile?.path.toString(),
                                username = item.user?.getUserName().toString(),
                                userImageUrl = item.user?.imageFile?.thumbnail_path.toString(),
                                date = time,
                                count = cnt,
                                type = item.type,
                                stayingTime = item.staying_time,
                                onClick = { clickListener?.onClickAuthItemCard() }
                            )
                            if (columnIndex == 0) {
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        } else {
                            Spacer(Modifier.weight(1f, fill = true))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(92.dp))
        }
    }
}

private fun <T> LazyListScope.gridItems(
    data: List<T>,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = if (data.isEmpty()) 0 else 1 + (data.count() - 1) / nColumns
    items(rows) { rowIndex ->
        Row(horizontalArrangement = horizontalArrangement) {
            for (columnIndex in 0 until nColumns) {
                val itemIndex = rowIndex * nColumns + columnIndex
                if (itemIndex < data.count()) {
                    val item = data[itemIndex]
                    androidx.compose.runtime.key(key?.invoke(item)) {
                        Box(
                            modifier = Modifier.weight(1f, fill = true),
                            propagateMinConstraints = true
                        ) {
                            itemContent.invoke(this, item)
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}