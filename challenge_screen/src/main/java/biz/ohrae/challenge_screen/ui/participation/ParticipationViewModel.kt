package biz.ohrae.challenge_screen.ui.participation

import androidx.lifecycle.ViewModel
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.ui.participation.ParticipationRepo
import biz.ohrae.challenge_repo.ui.register.RegisterRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import javax.inject.Inject

class ParticipationViewModel @Inject constructor(
    private val participationRepo: ParticipationRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : ViewModel() {
}