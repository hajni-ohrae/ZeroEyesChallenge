package biz.ohrae.challenge_repo.model.verify

import biz.ohrae.challenge_repo.model.detail.ImageFile
import biz.ohrae.challenge_repo.model.user.User


data class VerifyListState(
    val verifyListState:List<VerifyData>? = null
) {
    companion object {

    }

}

data class VerifyData(
    val  id: String,
    val  type: String,
    val  cnt: Int,
    val  staying_time: String,
    val  comment: String,
    val  created_date: String,
    val  updated_date: String,
    val user: User,
    val imageFile: ImageFile
)