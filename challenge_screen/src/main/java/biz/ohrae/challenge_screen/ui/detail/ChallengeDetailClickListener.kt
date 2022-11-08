package biz.ohrae.challenge_screen.ui.detail

import biz.ohrae.challenge_repo.model.user.User

interface ChallengeDetailClickListener {
    fun onClickParticipation()
    fun onClickAuth()
    fun onClickBookMark(like: Boolean)
    fun onClickReTakePhoto()
    fun onClickUsePhoto()
    fun onClickRedCardInfo()
    fun onClickShowAllChallengers()
    fun onDone(content: String)
    fun onClickAuthItemCard()
    fun onClickCaution()
    fun onClickChallengersResults()
}

interface ChallengeAuthFeedClickListener {
    fun onClickMine(isMine: Boolean)
    fun onClickOrder(isOrder: Boolean)
    fun onClickReport(
        verificationId: String,
        user: User
    )

    fun onClickLike(
        verificationId: String, like: Boolean
    )
}