package biz.ohrae.challenge_screen.ui.participation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.participation.ParticipationResult
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.ui.participation.ParticipationRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.ui.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParticipationViewModel @Inject constructor(
    private val participationRepo: ParticipationRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : BaseViewModel(prefs) {
    private val _cancelResult = MutableLiveData<Boolean?>()
    private val _participationResult = MutableLiveData<ParticipationResult>()

    val cancelResult get() = _cancelResult
    val participationResult get() = _participationResult

    fun registerChallenge(challengeData: ChallengeData, paidAmount: Int, rewardsAmount: Int, depositAmount: Int) {
        viewModelScope.launch {
            val response = participationRepo.registerChallenge(challengeData, paidAmount, rewardsAmount, depositAmount)

            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    _participationResult.value = it as ParticipationResult
                } ?: run {
                    _participationResult.value = null
                    setErrorData(result.errorCode, result.errorMessage)
                }
            }
        }
    }

    fun cancelChallenge(challengeData: ChallengeData) {
        viewModelScope.launch {
            val response = participationRepo.cancelChallenge(challengeData)

            response.flowOn(Dispatchers.IO).collect {
                val result = it.data as Boolean?
                _cancelResult.value = result
                if (result != true) {
                    setErrorData(it.errorCode, it.errorMessage)
                }
            }
        }
    }
}