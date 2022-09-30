package biz.ohrae.challenge_screen.ui.participation

interface ParticipationClickListener {
    fun onClickPayment(paidAmount: Int, rewardAmount: Int, depositAmount: Int)
    fun onClickCancelParticipation()
    fun onClickCancelResult()
    fun onClickPaymentDetail()
}