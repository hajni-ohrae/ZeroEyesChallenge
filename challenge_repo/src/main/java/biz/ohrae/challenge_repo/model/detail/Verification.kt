package biz.ohrae.challenge_repo.model.detail

data class Verification(
    val day: Int,
    // state - -1 : hidden, 0 : normal, 1 : success, 2: fail
    val status: String
) {
    companion object {
        const val HIDDEN = "hidden"
        const val FAILURE = "failure"
        const val SUCCESS = "success"
        const val REMAINING = "remaining"
    }
}
