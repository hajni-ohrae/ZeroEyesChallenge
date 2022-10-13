package biz.ohrae.challenge_screen.model.detail

data class Verification(
    val day: Int,
    // state - -1 : hidden, 0 : normal, 1 : success, 2: fail
    var state: Int
) {
    companion object {
        const val HIDDEN = -1
        const val NORMAL = 0
        const val SUCCESS = 1
        const val FAIL = 2
    }
}
