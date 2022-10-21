package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.user.*
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.model.user.RedCard
import biz.ohrae.challenge_screen.model.user.RedCardListState
import biz.ohrae.challenge_screen.model.user.UserChallengeListState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
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
    private val _rewardList = MutableLiveData<List<RewardData>>()
    private val _userData = MutableLiveData<User>()
    private val _userRedCardListPage = MutableLiveData(1)
    private val _userPaymentHistoryListPage = MutableLiveData(1)


    val redCardListState get() = _redCardListState
    val paymentHistoryState get() = _paymentHistoryState
    val userData get() = _userData
    val rewardList get() = _rewardList
    val userRedCardListPage get() = _userRedCardListPage
    val userPaymentHistoryListPage get() = _userPaymentHistoryListPage

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

    fun getRedCardList(
        isInit: Boolean = false,
    ) {
        viewModelScope.launch {
            if (isInit) {
                _userRedCardListPage.value = 1
            }
            val page = _userRedCardListPage.value ?: 1
            val response = userRepo.getRedCardList()
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val pager = it.pager

                    if (it.pager?.page == page) {
                        _userRedCardListPage.value = page + 1
                    }

                    val redCardState = data as List<RedCard>
                    val state = RedCardListState(redCardState)
                    _redCardListState.value = state
                }
            }
        }
    }

    fun getPaymentHistory(
        isInit: Boolean = false,
    ) {
        if (isInit) {
            _userRedCardListPage.value = 1
        }
        val page = _userRedCardListPage.value ?: 1

        viewModelScope.launch {
            val response = userRepo.getPaymentHistory()
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->

                    val pager = it.pager

                    if (it.pager?.page == page) {
                        _userPaymentHistoryListPage.value = page + 1
                    }
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

    fun getRewardHistory() {
        viewModelScope.launch {
            val response = userRepo.getRewardHistory()
            response.flowOn(Dispatchers.IO).collect() {
                if (it.data != null) {
                    val rewardList = it.data as List<RewardData>
                    _rewardList.value = rewardList
                }
            }
        }
    }

}