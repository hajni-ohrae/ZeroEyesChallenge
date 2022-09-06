package biz.ohrae.challenge_screen.ui.register

interface RegisterClickListener {
    fun onClickAuthNext(auth: String)
    fun onClickOpenNext(startDate:String,perWeek: String,verificationPeriodType:String)
    fun onClickRecruitmentNext()
    fun onClickChallengeCreate(auth: String, precautions: String, imgUrl: String? = null)
    fun onClickSelectedAuth(auth: String)
    fun onClickDropDownItem(item:String)
}