package biz.ohrae.challenge_screen.ui.register

interface RegisterClickListener {
    fun onClickAuthNext(auth: String)
    fun onClickOpenNext(gole: String)
    fun onClickRecruitmentNext(auth: String)
    fun onClickChallengeCreate(auth: String, precautions: String, imgUrl: String? = null)
    fun onClickSelectedAuth(auth: String)

}