package biz.ohrae.challenge_screen.ui.register

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.register.ImageBucket
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.ui.register.RegisterRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.register.ChallengeOpenState
import biz.ohrae.challenge_screen.ui.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChallengeRegisterViewModel @Inject constructor(
    private val registerRepo: RegisterRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : BaseViewModel(prefs) {
    private val _challengeData = MutableLiveData<ChallengeData>()
    private val _createdChallengeId = MutableLiveData<String>()
    private val _challengeImageUri = MutableLiveData<Uri?>(null)
    private val _checkAdultOnly = MutableLiveData<Int?>(0)
    private val _screenState = MutableLiveData<ChallengeOpenState>()
    private val _selectDay = MutableLiveData<String?>(null)
    private val _startDay = MutableLiveData<String?>(null)
    private val _endDay = MutableLiveData<String?>(null)
    private val _uploadedImage = MutableLiveData<ImageBucket>(null)

    val challengeData get() = _challengeData
    val createdChallengeId get() = _createdChallengeId
    val challengeImageUri get() = _challengeImageUri
    val checkAdultOnly get() = _checkAdultOnly
    val screenState get() = _screenState
    val selectDay get() = _selectDay
    val startDay get() = _startDay
    val endDay get() = _endDay
    val uploadedImage get() = _uploadedImage

    init {
        _screenState.value = ChallengeOpenState.mock()
    }

    fun createChallenge(challengeData: ChallengeData, imageFileId: String?) {
        viewModelScope.launch {
            val userId = prefs.getUserData()?.id
            val response = registerRepo.createChallenge(userId.toString(), challengeData, imageFileId)
            response.flowOn(Dispatchers.IO).collect { it ->
                it.data?.let { data ->
                    _createdChallengeId.value = data as String
                } ?: run {
                    _createdChallengeId.value = null
                    setErrorData(it.errorCode, it.errorMessage)
                }
            }
        }
    }

    fun uploadChallengeImage(imageFilePath: String) {
        viewModelScope.launch {
            val response = registerRepo.uploadImage(imageFilePath)
            response.flowOn(Dispatchers.IO).collect { it ->
                it.data?.let { data ->
                    val imageBucket = data as ImageBucket
                    _uploadedImage.value = imageBucket
                } ?: run {
                    _uploadedImage.value = null
                }
            }
        }
    }

    fun selectAuth(auth: String) {
        viewModelScope.launch {
            val state = ChallengeData.mock().copy()
            state.let {
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

                it.start_date = Utils.getDefaultChallengeDate()
                it.end_date = Utils.addWeeks(it.start_date.toString(), 1)
                _challengeData.value = it
            }
        }
    }

    fun verificationPeriodType() {
        viewModelScope.launch {
            val state = _challengeData.value?.copy()
            state?.let {
                val applyStartDate = Utils.sdf3().format(Date())
                it.apply_start_date = applyStartDate

                val remainDays = Utils.getDifferenceDays(
                    it.apply_start_date.toString(),
                    it.start_date.toString()
                )

                val list = mutableListOf<DropDownItem>()
                loop@ for(i in 1..remainDays) {
                    if (i >= 15) {
                        break@loop
                    }
                    list.add(DropDownItem("${i}일", i.toString()))
                }

                it.apply_end_date = Utils.addDays(it.apply_start_date.toString(), list.size - 1)

                val state2 = _screenState.value?.copy()
                state2?.let { screenState ->
                    screenState.recruitPeriod = list
                    _screenState.value = screenState
                }

                _challengeData.value = it
            }
        }
    }

    fun challengeGoals(goal: String, precautions: String, imageFileId: String?) {
        viewModelScope.launch {
            val state = _challengeData.value?.copy()
            state?.let {
                it.goal = goal
                it.caution = precautions

                _challengeData.value = it
                createChallenge(_challengeData.value!!, imageFileId)
            }
        }
    }

    fun selectPeriod(weeks: Int) {
        val state = _challengeData.value?.copy()
        state?.let {
            it.end_date = Utils.addWeeks(it.start_date.toString(), weeks)
            it.period = weeks
            _challengeData.value = it
        }
    }

    fun selectPeriodType(item: String) {
        val state = _challengeData.value?.copy()
        state?.let {
            if (item.length == 1){
                it.verification_period_type = "per_week"
                it.per_week = item.toInt()
            } else {
                it.verification_period_type = item
            }
            _challengeData.value = it
        }
    }

    fun checkAdultOnly(checked: Boolean) {
        val state = _challengeData.value?.copy()
        state?.let {
            if (checked) {
                it.is_adult_only = 1
            } else {
                it.is_adult_only = 0
            }
            _challengeData.value = it
        }
    }

    fun setChallengeImage(uri: Uri) {
        _challengeImageUri.value = uri
    }

    fun setRecruitDays(days: Int) {
        val state = _challengeData.value?.copy()
        state?.let {
            it.apply_end_date = Utils.addDays(it.apply_start_date.toString(), days - 1)
            _challengeData.value = it
        }
    }

    fun selectDay(day:String){
        _selectDay.value = day
    }

    fun setStartDay(day:String){
        val state = _challengeData.value?.copy()
        state?.let {
            it.start_date = day
            _challengeData.value = it
        }
    }
}