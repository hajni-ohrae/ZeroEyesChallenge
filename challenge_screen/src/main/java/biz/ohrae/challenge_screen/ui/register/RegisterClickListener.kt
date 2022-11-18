package biz.ohrae.challenge_screen.ui.register

import android.net.Uri
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem

interface RegisterClickListener {
    fun onClickAuthNext(auth: String)
    fun onClickOpenNext(weeks: Int)
    fun onClickRecruitmentNext()
    fun onClickChallengeCreate(goal: String, precautions: String, imgUrl: Uri?)
    fun onClickSelectedAuth(auth: String)
    fun onClickPeriod(item: DropDownItem)
    fun onClickPeriodType(item: String)
    fun onClickPhotoBox()
    fun onClickReTakePhoto()
    fun onClickUsePhoto()
    fun onClickCalendar()
    fun onClickRecruitDays(item: DropDownItem)
    fun onClickAgeLimitType(item: DropDownItem)
    fun onClickHoursOfUse(item: String)
}