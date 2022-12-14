package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatGrayButton
import biz.ohrae.challenge.ui.components.list_item.RankItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.detail.VerificationState
import biz.ohrae.challenge_screen.util.OnBottomReached
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengersResultsScreen(
    challengeData: ChallengeData? = ChallengeData.mock(),
    challengers: List<User>? = null,
    verificationState: VerificationState? = null,
    challengeVerifiedList: List<VerifyData>? = null,
    clickListener: ChallengeDetailClickListener? = null,
    onBottomReached: () -> Unit = {}
) {
    val pagerState = rememberPagerState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val authType =
        if (challengeData?.is_verification_photo == 1) "photo" else if (challengeData?.is_verification_checkin == 1) "checkin" else "time"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        ColumnForLazy {
            TabRow(
                modifier = Modifier
                    .fillMaxWidth().height(56.dp),
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
                        text = "챌린저스",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = listState
        ) {
            items(1) {
                ColumnForLazy {
                    val localDensity = LocalDensity.current
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
                            if (challengers != null) {
                                ResultChallengers(
                                    modifier = Modifier.fillMaxWidth().padding(24.dp, 0.dp).onGloballyPositioned { coordinates ->
                                        pagerHeight = with(localDensity) { coordinates.size.height.toDp() }
                                    },
                                    challengers = challengers,
                                    clickListener = clickListener,
                                    authType = authType
                                )
                            }
                        } else {
                            ChallengeAuthPage(
                                modifier = Modifier.fillMaxWidth().padding(24.dp, 0.dp),
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
                        Timber.e("bottom reached!!")
                        onBottomReached()
                    }
                }
            }
        }
    }
}

@Composable
fun ResultChallengers(
    modifier: Modifier = Modifier,
    challengers: List<User>,
    clickListener: ChallengeDetailClickListener? = null,
    authType:String
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(30.dp))
        challengers.forEachIndexed { index, user ->
            if (index < 10) {
                val timeDays = when (authType) {
                    "photo" -> "${user.inChallenge?.get(0)?.verification_cnt.toString()}회"
                    "checkin" -> "${user.inChallenge?.get(0)?.verification_cnt.toString()}일"
                    else -> Utils.getTimeDays(user.inChallenge?.get(0)?.verification_time.toString())
                }
                RankItem(
                    userName = user.getUserName(),
                    rank = user.inChallenge?.get(0)?.ranking.toString(),
                    timeDays = timeDays,
                    progress = "${user.inChallenge?.get(0)?.achievement_percent.toString()}%",
                    profileImage = user.imageFile?.thumbnail_path,
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (challengers.isNotEmpty()) {
            Spacer(modifier = Modifier.height(17.dp))
            FlatGrayButton(
                modifier = Modifier
                    .fillMaxWidth().height(44.dp),
                text = "+더보기",
                onClick = {
                    clickListener?.onClickShowAllChallengers(authType)
                }
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}