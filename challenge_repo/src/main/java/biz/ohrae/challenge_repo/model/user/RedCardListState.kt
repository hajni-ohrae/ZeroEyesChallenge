package biz.ohrae.challenge_repo.model.user

import androidx.compose.runtime.snapshots.SnapshotStateList
data class RedCardListState(
    var redCardState: SnapshotStateList<RedCardState>? = null,
    var redCardPage: Int? = null,
    var totalRedCard: Int
){

}
data class RedCardState(
    val id: Int?,
    val reason: String?,
    val is_valid: Int?,
    val canceled_reason: String?,
    val canceled_date: String?,
    val created_date: String?,
    val updated_date: String?,
) {

}
