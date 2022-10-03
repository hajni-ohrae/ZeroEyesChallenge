package biz.ohrae.challenge_screen.ui.detail

interface ChallengeDetailClickListener {
    fun onClickParticipation()
    fun onClickAuth()
    fun onClickReTakePhoto()
    fun onClickUsePhoto()
    fun onDone(content: String)
}