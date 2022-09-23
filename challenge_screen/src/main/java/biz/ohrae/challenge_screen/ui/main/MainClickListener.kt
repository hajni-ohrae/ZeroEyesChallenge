package biz.ohrae.challenge_screen.ui.main

interface MainClickListener {
    fun onClickPurchaseTicket()
    fun onClickRegister()
    fun onClickChallengeItem(id: String)
    fun onClickFilterItem(paymentType:String)
}