package biz.ohrae.challenge_repo.model.verify

import biz.ohrae.challenge.model.filter.FilterItem
import biz.ohrae.challenge.model.list_item.ChallengeItemData
import biz.ohrae.challenge_repo.model.detail.ImageFile
import biz.ohrae.challenge_repo.model.user.User


data class VerifyListState(
    val verifyList: List<VerifyData>? = null
) {
    companion object {
        fun mock() = VerifyListState(
            verifyList = listOf(
                VerifyData(
                    id = "a858a07c-2e0f-4c80-be5b-e8cac8d98056",
                    type = "photo",
                    cnt = 1,
                    staying_time = "",
                    comment = "gg",
                    created_date = "",
                    updated_date = "",
                    is_like = 0
                )
            ),
        )
    }

}

data class VerifyData(
    val id: String,
    val type: String,
    val cnt: Int,
    val staying_time: String,
    val comment: String,
    val created_date: String,
    val updated_date: String,
    val user: User? = null,
    val imageFile: ImageFile? = null,
    val is_like:Int,
) {
    companion object {
        fun mock() = VerifyData(
            id = "a858a07c-2e0f-4c80-be5b-e8cac8d98056",
            type = "photo",
            cnt = 1,
            staying_time = "",
            comment = "gg",
            created_date = "",
            updated_date = "",
            is_like = 0
        )
    }
}