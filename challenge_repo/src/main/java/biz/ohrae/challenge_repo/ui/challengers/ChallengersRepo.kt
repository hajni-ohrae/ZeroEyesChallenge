package biz.ohrae.challenge_repo.ui.challengers

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import javax.inject.Inject

class ChallengersRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {

}