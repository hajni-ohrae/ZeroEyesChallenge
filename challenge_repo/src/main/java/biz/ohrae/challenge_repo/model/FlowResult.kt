package biz.ohrae.challenge_repo.model

import biz.ohrae.challenge_repo.util.PagerMeta

interface CopyableWithId<out T> where T: CopyableWithId<T> {
    fun copy(newId: Long): T
    val id: Long
}

data class FlowResult(
    val data: Any?,
    val errorCode : String?,
    val errorMessage: String?,
    val pager: PagerMeta? = null,
)