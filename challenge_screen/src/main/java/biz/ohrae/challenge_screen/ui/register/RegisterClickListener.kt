package biz.ohrae.challenge_screen.ui.register

interface RegisterClickListener {
    fun onClickAuthNext(auth:String)
    fun onClickOpenNext(auth:String)
    fun onClickRecruitmentNext(auth:String)
    fun onClickChallengeCreate(auth:String)
    fun onClickSelectedAuth(auth:String)

}