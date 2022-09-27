package biz.ohrae.challenge_repo.model.user

data class ZeCustomer(
    val id: String,
    val access_token: String,
    val name: String?,
    val nick_name: String?,
    val phone_number: String
)