package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.card.CertificationImageItem
import biz.ohrae.challenge.ui.components.filter.FeedFilter
import biz.ohrae.challenge.ui.components.filter.FeedItem
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.util.prefs.Utils
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengeAuthFeedScreen(
    challengeVerifiedList: List<VerifyData>? = null,
    clickListener: ChallengeDetailClickListener? = null
) {
    Column(modifier = Modifier.background(Color(0xfff7f7f7))) {
        FeedFilter()
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(33.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                crossAxisSpacing = 8.dp,
                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
            ) {
                challengeVerifiedList?.let {
                    repeat(it.size) { index ->
                        val item = challengeVerifiedList[index]
                        FeedItem(
                            imageUrl = item.imageFile?.thumbnail_path.toString(),
                            username = item.user?.getUserName().toString(),
                            date = Utils.convertDate(item.updated_date),
                            count = item.cnt,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}