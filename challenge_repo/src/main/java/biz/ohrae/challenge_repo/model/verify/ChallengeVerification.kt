package biz.ohrae.challenge_repo.model.verify

data class ChallengeVerification(
    val id: String,
    val type: String,
    val cnt: Int,
    val day: Int,
    val checkin_date:String?,
    val staying_time: String?,
    val comment: String?,
    val created_date: String,
    val updated_date: String,
)
