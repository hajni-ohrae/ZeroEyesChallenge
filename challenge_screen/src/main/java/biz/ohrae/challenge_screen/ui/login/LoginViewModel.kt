package biz.ohrae.challenge_screen.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.login.Auth
import biz.ohrae.challenge_repo.ui.login.LoginRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : ViewModel() {
    private val _checkedAuth = MutableLiveData(false)
    private val _receivedAuth = MutableLiveData(false)
    private val _relatedServiceIds: MutableLiveData<List<String>> = MutableLiveData()

    val checkedAuth get() = _checkedAuth
    val receivedAuth get() = _receivedAuth
    val relatedServiceIds get() = _relatedServiceIds

    fun requestAuth(phoneNumber: String, deviceUid: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("phone_number", phoneNumber)
        jsonObject.addProperty("os", "android")
        jsonObject.addProperty("app_version", "10.0")
        jsonObject.addProperty("shop_id", "mooin_test")
        viewModelScope.launch {
            val response = loginRepo.auth(deviceUid, phoneNumber)

            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val auth = it as Auth
                    _receivedAuth.value = true
                }
            }
        }
    }

    fun authCheck(phoneNumber: String, authNumber: String) {
        viewModelScope.launch {
            val response = loginRepo.authCheck(phoneNumber, authNumber)

            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val success = it as Boolean
                    _checkedAuth.value = success
                    if (success) {
                        relate()
                    }
                }
            }
        }
    }

    fun relate() {
        viewModelScope.launch {
            val response = loginRepo.relate()
            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val serviceIds = it as List<String>
                    if (serviceIds.isNotEmpty()) {
                        _relatedServiceIds.value = serviceIds
                    }
                }
            }
        }
    }
}