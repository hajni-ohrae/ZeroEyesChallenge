package biz.ohrae.challenge_screen.ui.mychallenge

interface MyChallengeClickListener {
    fun onClickReward()
    fun onClickPaymentDetail()
    fun onClickSavedChallenge()
    fun onClickRedCard()
    fun onClickApplyWithdraw()
    fun onClickApplyWithdrawDetail()
    fun onClickPolicy(screen:String)
}