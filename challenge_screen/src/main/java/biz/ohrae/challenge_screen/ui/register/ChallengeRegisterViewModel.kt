package biz.ohrae.challenge_screen.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.ui.register.RegisterRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeRegisterViewModel @Inject constructor(
    private val registerRepo: RegisterRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : ViewModel() {
    private val _challengeData = MutableLiveData<ChallengeData>()
    private val _isChallengeCreate = MutableLiveData(false)

    val challengeData get() = _challengeData
    val isChallengeCreate get() = _isChallengeCreate


    fun createChallenge(challengeData: ChallengeData){
        viewModelScope.launch {
            val userId = prefs.getUserData()?.id
            val response = registerRepo.createChallenge(userId.toString(),challengeData)
            response.flowOn(Dispatchers.IO).collect { it ->
                it.data?.let { data ->
                    _isChallengeCreate.value = data as Boolean
                    if (!data){

                    }
                }
            }
        }

    }

    fun selectAuth(auth:String){
        viewModelScope.launch {
            val state = challengeData.value?.copy()
            state?.let {
                when (auth) {
                    "photo" -> {
                        it.is_verification_photo = 1
                    }
                    "checkIn" -> {
                        it.is_verification_checkin = 1
                    }
                    "time" -> {
                        it.is_verification_time = 1
                    }
                }
            }

        }
    }

}