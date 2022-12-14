package biz.ohrae.challenge_screen.ui.participation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.participation.PaidInfo
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
    private val _cancelResult = MutableLiveData<ParticipationResult?>()
    private val _participationResult = MutableLiveData<ParticipationResult>()
    private val _paidInfo = MutableLiveData<PaidInfo>()
    private val _checkedChallenge = MutableLiveData<Boolean>()

    val cancelResult get() = _cancelResult
    val participationResult get() = _participationResult
    val paidInfo get() = _paidInfo
    val checkedChallenge get() = _checkedChallenge

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
            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    _cancelResult.value = it as ParticipationResult
                } ?: run {
                    _cancelResult.value = null
                    setErrorData(result.errorCode, result.errorMessage)
                }
            }
        }
    }

    fun setPaymentInfo(cardName: String, amount: String, rewardsAmount: String) {
        viewModelScope.launch {
            _paidInfo.value = PaidInfo(cardName, amount.toInt(), rewardsAmount.toInt())
        }
    }

    fun checkChallenge(challengeData: ChallengeData) {
        viewModelScope.launch {
            val response = participationRepo.checkChallenge(challengeData)

            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val success = it as Boolean
                    if (!success) {
                        setErrorData(result.errorCode, result.errorMessage)
                    }
                    _checkedChallenge.value = success
                } ?: run {
                    _checkedChallenge.value = false
                    setErrorData(result.errorCode, result.errorMessage)
                }
            }
        }
    }
}