import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.WelcomeScreenState
import biz.ohrae.challenge.ui.components.button.FlatButton
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.appColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true
)
@Composable
fun WelcomeScreen(
    state: WelcomeScreenState = WelcomeScreenState(),
    onPageChanged: (page: Int) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        scope.launch {
            pagerState.animateScrollToPage(state.currentPage)
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                onPageChanged(page)
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = 3,
            state = pagerState
        ) {
            WelcomeContent(currentPage = currentPage, resId = state.list[currentPage].resId)
        }
    }
}

@Composable
private fun WelcomeContent( currentPage: Int, resId: Int) {
    val backgroundColor = when (currentPage) {
        0 -> appColor.PointColor
        1 -> appColor.PointSubColor
        2 -> appColor.AlertWarningSubColor
        else -> DefaultBlack
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = resId),
            contentDescription = resId.toString(),
            contentScale = ContentScale.FillWidth
        )
    }
}