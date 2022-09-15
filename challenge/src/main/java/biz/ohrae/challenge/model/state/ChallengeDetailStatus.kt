package biz.ohrae.challenge.model.state

import androidx.compose.ui.graphics.Color

data class ChallengeDetailStatus(
    val status: String,
    val backgroundColor: Color,
    val textColor: Color
) {
    companion object {
        fun mock() = ChallengeDetailStatus(
            "모집중",
            Color(0xffebfaf1),
            Color(0xff219653)
        )
    }
}
