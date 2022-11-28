package biz.ohrae.challenge_repo.model.profile

data class NicknameState(
    var success: Boolean,
    var message: String,
    val is_nickname_isolated: Int?,
    val is_nickname_valid: Int?
)
