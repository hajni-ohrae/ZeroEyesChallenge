package biz.ohrae.challenge_screen.model.detail

data class VerificationState(
    val successCount: Int,
    val remainCount: Int,
    val failCount: Int,
    var verifications: List<Verification>? = null
)
