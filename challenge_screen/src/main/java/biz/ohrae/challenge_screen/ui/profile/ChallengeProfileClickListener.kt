package biz.ohrae.challenge_screen.ui.profile

interface ChallengeProfileClickListener {
    fun onClickProfileImage()
    fun onClickIdentityVerification()
    fun onClickChangeNickname(nickname: String?)
}