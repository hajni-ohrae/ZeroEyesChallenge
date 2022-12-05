package biz.ohrae.challenge_repo.model.user

import biz.ohrae.challenge_repo.model.account.BankAccount
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
    val summary: Summary?,
    var access_token: String,
    val phone_number: String,
    var refresh_token: String,
    val thumbnail_path: String,
    val ranking: String?,
    val is_identified: Int,
    val inChallenge: List<InChallenge>?,
    val main_color: String,
    val gender: Int,
    val is_nickname_valid: Int?,
    val has_bank_account: Int?,
    val bank_account: BankAccount?,
) {
    companion object {
        fun mock() = User(
            id = "",
            type = "",
            name = "이름",
            nickname = "닉네임",
            birth_date = "2000-01-01",
            is_blocked = 0,
            rewards_amount = 0,
            monthly_expire_rewards_amount = 0,
            total_deposit_amount = 0,
            total_refund_amount = 0,
            total_transfer_amount = 0,
            total_rewards_amount = 0,
            total_redcard_cnt = 0,
            total_report_cnt = 0,
            retirement_date = "",
            blocked_date = "",
            created_date = "",
            updated_date = "",
            inService = null,
            imageFile = null,
            summary = null,
            access_token = "",
            refresh_token = "",
            phone_number = "01095357292",
            thumbnail_path = "",
            ranking = null,
            is_identified = 0,
            inChallenge = null,
            main_color = "",
            gender = 0,
            is_nickname_valid = 0,
            has_bank_account = 0,
            bank_account = null
        )
    }

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