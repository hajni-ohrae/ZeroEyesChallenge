package biz.ohrae.challenge_repo.model.user

data class User(
    val access_token: String,
    val created_date: String,
    val id: String,
    val nick_name: String,
    val phone_number: String,
    val refresh_token: String
)