package biz.ohrae.challenge_screen.ui.register

interface RegisterClickListener {
    fun onClickAuthNext(auth: String)
    fun onClickOpenNext(startDate: String, perWeek: String, verificationPeriodType: String)
    fun onClickRecruitmentNext()
    fun onClickChallengeCreate(auth: String, precautions: String, imgUrl: String = "")
    fun onClickSelectedAuth(auth: String)
    fun onClickPeriod(item: String)
    fun onClickPeriodType(item: String)
    fun onClickPhotoBox()
    fun onClickReTakePhoto()
    fun onClickUsePhoto()
    fun onClickCalendar()
}