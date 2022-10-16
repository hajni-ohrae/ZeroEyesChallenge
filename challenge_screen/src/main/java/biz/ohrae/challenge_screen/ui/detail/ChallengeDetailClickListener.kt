package biz.ohrae.challenge_screen.ui.detail

interface ChallengeDetailClickListener {
    fun onClickParticipation()
    fun onClickAuth()
    fun onClickReTakePhoto()
    fun onClickUsePhoto()
    fun onClickRedCardInfo()
    fun onClickShowAllChallengers()
    fun onDone(content: String)
    fun onClickAuthItemCard()
}