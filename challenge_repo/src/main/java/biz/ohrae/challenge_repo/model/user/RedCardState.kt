package biz.ohrae.challenge_repo.model.user

import biz.ohrae.challenge_repo.model.user.User

data class RedCardState(
    val id: String,
    val reason: String,
    val is_valid: String,
    val canceled_reason: String,
    val canceled_date: String,
    val created_date: String,
    val updated_date: String,
) {

}
