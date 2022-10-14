package biz.ohrae.challenge_repo.model.verify

data class ChallengeVerification(
    val id: String,
    val type: String,
    val cnt: Int,
    val day: Int,
    val staying_time: String?,
    val comment: String?
)
