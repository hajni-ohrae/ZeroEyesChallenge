package biz.ohrae.challenge.ui.components.indicator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Preview(
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun DotIndicatorGallery() {
    Column(modifier = Modifier.fillMaxWidth()) {
        DotIndicator(
            modifier = Modifier.fillMaxWidth(),
            size = 10,
            currentIndex = 4
        )
        DotIndicator(
            modifier = Modifier.fillMaxWidth(),
            size = 3,
            currentIndex = 0
        )
    }
}

@Composable
fun DotIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    currentIndex: Int,
    color: Color = Color(0xff4985f8)
) {
    FlowRow(
        modifier = modifier,
        mainAxisAlignment = MainAxisAlignment.Center,
        crossAxisAlignment = FlowCrossAxisAlignment.Center
    ) {
        for(i in 0 until size) {
            val alpha = if (i == currentIndex) {
                1f
            } else {
                0.2f
            }

            Surface(
                modifier = Modifier
                    .size(8.dp)
                    .aspectRatio(1f)
                    .alpha(alpha),
                color = color,
                shape = RoundedCornerShape(4.dp)
            ) {}
            if (i < size - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}