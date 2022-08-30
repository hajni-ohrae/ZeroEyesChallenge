package biz.ohrae.challenge_repo.ui.register

import biz.ohrae.challenge.model.MainScreenState
import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun createChallenge() {
        val jsonObject = JsonObject()
        val response = apiService.createChallenge(jsonObject)

        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {

                } else {

                }
            }
            else -> {
            }
        }
    }
}