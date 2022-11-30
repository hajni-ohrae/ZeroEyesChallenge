package biz.ohrae.challenge_screen.ui.mychallenge

data class AccountAuthScreenState(
    var buttonName: String,
    var state: String,
    var isAuthed: Boolean = false,
    var buttonEnabled: Boolean = false,
) {
    companion object {
        fun mock() = AccountAuthScreenState(
            buttonName = "인증",
            state = "auth",
            isAuthed = false,
            buttonEnabled = false
        )
    }
}
