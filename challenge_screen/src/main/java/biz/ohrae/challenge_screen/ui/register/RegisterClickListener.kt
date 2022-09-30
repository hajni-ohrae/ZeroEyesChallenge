package biz.ohrae.challenge_screen.ui.register

import biz.ohrae.challenge.ui.components.dropdown.DropDownItem

interface RegisterClickListener {
    fun onClickAuthNext(auth: String)
    fun onClickOpenNext(perWeek: String, verificationPeriodType: String)
    fun onClickRecruitmentNext()
    fun onClickChallengeCreate(auth: String, precautions: String, imgUrl: String?)
    fun onClickSelectedAuth(auth: String)
    fun onClickPeriod(item: DropDownItem)
    fun onClickPeriodType(item: String)
    fun onClickPhotoBox()
    fun onClickReTakePhoto()
    fun onClickUsePhoto()
    fun onClickCalendar()
    fun onClickRecruitDays(item: DropDownItem)
}