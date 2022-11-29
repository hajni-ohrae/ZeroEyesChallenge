package biz.ohrae.challenge_screen.ui.main

interface MainClickListener {
    fun onClickPurchaseTicket()
    fun onClickRegister()
    fun onClickChallengeItem(id: String)
    fun onClickFilterType(filterType: String)
    fun onClickChallengeAuthItem(id: String, type: Int = 0)
    fun onClickMyChallengeCard(id: String)
    fun onClickTopBanner()
}