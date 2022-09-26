package biz.ohrae.challenge_screen.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.detail.ChallengeData
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
    private val _challengeImageUri = MutableLiveData<String?>(null)
    private val _checkAdultOnly = MutableLiveData<Int?>(0)

    val challengeData get() = _challengeData
    val isChallengeCreate get() = _isChallengeCreate
    val challengeImageUri get() = _challengeImageUri
    val checkAdultOnly get() = _checkAdultOnly

    fun createChallenge(challengeData: ChallengeData) {
        viewModelScope.launch {
            val userId = prefs.getUserData()?.id
            val response = registerRepo.createChallenge(userId.toString(), challengeData)
            response.flowOn(Dispatchers.IO).collect { it ->
                it.data?.let { data ->
                    _isChallengeCreate.value = data as Boolean
                    if (!data) {

                    }
                }
            }
        }
    }

    fun selectAuth(auth: String) {
        viewModelScope.launch {
            val state = ChallengeData.mock().copy()
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
                _challengeData.value = it
            }
        }
    }

    fun verificationPeriodType(
        startDay: String,
        perWeek: String,
        verificationPeriodType: String
    ) {
        viewModelScope.launch {
            val state = _challengeData.value?.copy()
            state?.let {
                it.start_date = startDay
                it.per_week = perWeek.toInt()
                it.verification_period_type = verificationPeriodType

                _challengeData.value = it
            }
        }
    }

    fun challengeGoals(goal: String, precautions: String, imgUrl: String) {
        viewModelScope.launch {
            val state = _challengeData.value?.copy()
            state?.let {
                it.goal = goal
                it.subject = precautions
                it.image_path = imgUrl

                _challengeData.value = it
                createChallenge(_challengeData.value!!)
            }
        }
    }

    fun selectPeriod(item:String){
        val state = _challengeData.value?.copy()
        state?.let {
            it.period = item.toInt()
            _challengeData.value = it
        }
    }

    fun selectPeriodType(item:String){
        val state = _challengeData.value?.copy()
        state?.let {
            it.verification_period_type = item
            _challengeData.value = it
        }
    }

    fun checkAdultOnly(checked: Boolean) {
        val state = _challengeData.value?.copy()
        state?.let {
            if (checked){
                _checkAdultOnly.value = 1
            }else {
                _checkAdultOnly.value = 0
            }
            _challengeData.value = it
        }
    }

    fun setChallengeImage(uri: String) {
        _challengeImageUri.value = uri
    }
}