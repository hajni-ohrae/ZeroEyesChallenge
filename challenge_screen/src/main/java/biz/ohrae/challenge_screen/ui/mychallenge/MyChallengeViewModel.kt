package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.user.PaymentHistoryData
import biz.ohrae.challenge_repo.model.user.PaymentHistoryState
import biz.ohrae.challenge_repo.model.user.RedCardState
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.model.user.RedCard
import biz.ohrae.challenge_screen.model.user.RedCardListState
import biz.ohrae.challenge_screen.model.user.UserChallengeListState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyChallengeViewModel @Inject constructor(
    private val registerRepo: ChallengeMainRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : ViewModel() {
    private val _redCardListState = MutableLiveData<RedCardListState>()
    private val _paymentHistoryState = MutableLiveData<PaymentHistoryState>()
    private val _userData = MutableLiveData<User>()

    val redCardListState get() = _redCardListState
    val paymentHistoryState get() = _paymentHistoryState
    val userData get() = _userData

    fun getAllBlock() {
        viewModelScope.launch {
            val response = userRepo.getAllBlock()
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val challengeList = data as List<RedCardState>

                }
            }
        }
    }

    fun getRedCardList() {
        viewModelScope.launch {
            val response = userRepo.getRedCardList()
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val redCardState = data as List<RedCard>
                    val state = RedCardListState(redCardState)
                    _redCardListState.value = state

                }
            }
        }
    }

    fun getPaymentHistory() {
        viewModelScope.launch {
            val response = userRepo.getPaymentHistory()
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val paymentHistoryState = data as List<PaymentHistoryData>
                    val state = PaymentHistoryState(paymentHistoryState)
                    _paymentHistoryState.value = state
                }
            }
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            val response = userRepo.getUserData()
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val userData = data as User
                    _userData.value = userData
                }
            }
        }
    }

}