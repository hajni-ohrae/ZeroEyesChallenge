package biz.ohrae.challenge_repo.model

interface CopyableWithId<out T> where T: CopyableWithId<T> {
    fun copy(newId: Long): T
    val id: Long
}

data class FlowResult(
    val data: Any?,
    val errorCode : String?,
    val errorMessage: String?,
)