package biz.ohrae.challenge_screen.ui.participation

import biz.ohrae.challenge_repo.model.detail.ChallengeData

interface ParticipationClickListener {
    fun onClickPayment(paidAmount: Int, rewardAmount: Int, depositAmount: Int)
    fun onClickCancelParticipation(isFree:Boolean)
    fun onClickCancelResult()
    fun onClickPaymentDetail()
    fun onClickHome()
    fun onClickSetAlarm()
}