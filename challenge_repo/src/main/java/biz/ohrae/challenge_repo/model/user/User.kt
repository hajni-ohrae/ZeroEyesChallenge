package biz.ohrae.challenge_repo.model.user

import androidx.compose.ui.graphics.Color
import biz.ohrae.challenge_repo.model.detail.ImageFile
import biz.ohrae.challenge_repo.model.detail.InChallenge
import biz.ohrae.challenge_repo.model.detail.Summary

data class User(
    val id: String,
    val type: String,
    val name: String?,
    val nickname: String?,
    val birth_date: String? = null,
    val is_blocked: Int,
    val rewards_amount: Int,
    val monthly_expire_rewards_amount: Int,
    val total_deposit_amount: Int,
    val total_refund_amount: Int,
    val total_transfer_amount: Int,
    val total_rewards_amount: Int,
    val total_report_cnt: Int,
    val total_redcard_cnt: Int,
    val retirement_date: String,
    val blocked_date: String,
    val created_date: String,
    val updated_date: String,
    val inService: List<InService>? = null,
    val imageFile: ImageFile?,
    val summary: Summary,
    var access_token: String,
    val phone_number: String,
    var refresh_token: String,
    val thumbnail_path: String,
    val ranking: String?,
    val is_identified: Int,
    val inChallenge: List<InChallenge>?,
    val main_color: String

) {
    fun getUserName(): String {
        return nickname ?: (name ?: "이름없음")
    }
}

data class InService(
    val id: String,
    val service_id: String,
    val type: String,
    val service_user_type: String,
    val brand: String,
    val created_date: String,
    val updated_date: String,

    )