package biz.ohrae.challenge_screen.ui.mychallenge

import biz.ohrae.challenge_repo.model.user.PaymentHistoryData

interface MyChallengeClickListener {
    fun onClickReward()
    fun onClickPaymentDetail()
    fun onClickSavedChallenge()
    fun onClickRedCard()
    fun onClickApplyWithdraw()
    fun onClickApplyWithdrawDetail(amountText: String)
    fun onClickPolicy(screen: String)
    fun onClickPolicyRefund()
    fun onClickChallengeAuthItem(id: String, type: Int = 0)
    fun onClickMyChallengeCard(id: String)
    fun onClickChallengeItem(id: String)
    fun onClickFilterType(type: String)
    fun onClickRewardFilterType(type: String)
    fun onClickProfile()
    fun onClickAccountAuth(isDone: Boolean)
    fun onClickRegisterAccountNumber(bankCode: String, accountNumber: String)
    fun onClickPaymentItem(paymentHistoryData: PaymentHistoryData)
}