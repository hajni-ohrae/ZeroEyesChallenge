package biz.ohrae.challenge_repo.model.user

import biz.ohrae.challenge_repo.model.detail.ImageFile

data class User(
    val access_token: String,
    val created_date: String,
    val updated_date: String,
    val id: String,
    val nickname: String?,
    val phone_number: String,
    val refresh_token: String,
    val type: String,
    val name: String,
    val birth_date: String? = null,
    val is_blocked: Int,
    val rewards_amount: Int,
    val imageFile: ImageFile
)