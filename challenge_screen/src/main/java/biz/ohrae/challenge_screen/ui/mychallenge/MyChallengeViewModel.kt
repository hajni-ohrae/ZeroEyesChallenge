package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge_repo.model.user.*
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.ui.mychallenge.MyChallengeRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.ui.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyChallengeViewModel @Inject constructor(
    private val myChallengeRepo: MyChallengeRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : BaseViewModel(prefs) {
    private val _redCardListState = MutableLiveData<RedCardListState>()
    private val _paymentHistoryState = MutableLiveData<SnapshotStateList<PaymentHistoryData>>()
    private val _rewardListState = MutableLiveData<SnapshotStateList<RewardData>>()
    private val _rewardFilter = MutableLiveData<RewardFilter>()
    private val _userData = MutableLiveData<User>()
    private val _userRedCardListPage = MutableLiveData(1)
    private val _rewardListPage = MutableLiveData(1)
    private val _userPaymentHistoryListPage = MutableLiveData(1)
    private val _isNicknameValid = MutableLiveData<Int?>()
    private val _accountScreenState = MutableLiveData<AccountAuthScreenState>()
    private val _bankList = MutableLiveData<List<DropDownItem>>()
    private val _accountRegistered = MutableLiveData<Boolean?>(null)
    private val _transferRewards = MutableLiveData<Boolean?>(null)

    val redCardListState get() = _redCardListState
    val paymentHistoryState get() = _paymentHistoryState
    val userData get() = _userData
    val rewardListState get() = _rewardListState
    val rewardFilter get() = _rewardFilter
    val userRedCardListPage get() = _userRedCardListPage
    val rewardListPage get() = _rewardListPage
    val userPaymentHistoryListPage get() = _userPaymentHistoryListPage
    val isNicknameValid get() = _isNicknameValid
    val accountScreenState get() = _accountScreenState
    val bankList get() = _bankList
    val accountRegistered get() = _accountRegistered
    val transferRewards get() = _transferRewards

    init {
        _accountScreenState.value = AccountAuthScreenState("??????", "register")
    }

    fun getRedCardList(
        isInit: Boolean = false,
    ) {
        viewModelScope.launch {
            if (isInit) {
                _userRedCardListPage.value = 1
            }
            val page = _userRedCardListPage.value ?: 1

            val response = userRepo.getRedCardList(page)
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->

                    val pager = it.pager
                    if (it.pager?.page == page) {
                        _userRedCardListPage.value = page + 1
                    }

                    val redCardList = it.data as SnapshotStateList<RedCardState>
                    val state = redCardListState.value?.copy()
                    val totalCount = pager?.total ?: 0
                    state?.let {
                        if (isInit) {
                            state.redCardState = redCardList
                        } else {
                            state.redCardState?.addAll(redCardList)
                            state.redCardPage = pager?.page
                        }
                        _redCardListState.value = state
                    } ?: run {
                        _redCardListState.value = RedCardListState(
                            redCardState = redCardList,
                            redCardPage = 1,
                            totalRedCard = totalCount
                        )
                    }
                } ?: run {
                    setErrorData(it.errorCode, it.errorMessage)
                }
            }
        }
    }

    fun getPaymentHistory(
        isInit: Boolean = false,
    ) {
        if (isInit) {
            _userPaymentHistoryListPage.value = 1
        }
        val page = _userPaymentHistoryListPage.value ?: 1

        viewModelScope.launch {
            val response = userRepo.getPaymentHistory(page)
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->

                    val pager = it.pager
                    if (it.pager?.page == page) {
                        _userPaymentHistoryListPage.value = page + 1
                    }
                    val paymentHistoryState = it.data as SnapshotStateList<PaymentHistoryData>

                    if (isInit) {
                        _paymentHistoryState.value = paymentHistoryState
                    } else {
                        _paymentHistoryState.value?.addAll(paymentHistoryState)
                    }
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
                    val tempUserData = prefs.getUserData()
                    userData.access_token = tempUserData?.access_token.toString()
                    userData.refresh_token = tempUserData?.refresh_token.toString()
                    prefs.setUserData(userData)
                    _isNicknameValid.value = userData.is_nickname_valid
                }
            }
        }
    }

    fun getRewardHistory(
        type: String,
        isInit: Boolean = false,
    ) {
        viewModelScope.launch {
            if (isInit) {
                _rewardListPage.value = 1
            }
            val page = _rewardListPage.value ?: 1

            val response = userRepo.getRewardHistory(type, page)
            response.flowOn(Dispatchers.IO).collect() {
                if (it.data != null) {

                    val pager = it.pager

                    if (it.pager?.page == page) {
                        _rewardListPage.value = page + 1
                    }

                    val rewardList = it.data as SnapshotStateList<RewardData>

                    if (isInit) {
                        _rewardListState.value = rewardList
                    } else {
                        _rewardListState.value?.addAll(rewardList)
                    }
                }
            }
        }
    }

    fun authAccountNumber() {
        viewModelScope.launch {
            val state = accountScreenState.value?.copy()
            state?.let {
                it.buttonName = "??????"
                it.state = "register"
                it.isAuthed = true
                _accountScreenState.value = it
            }
        }
    }

    fun registerAccountNumber(bankCode: String, bankAccount: String) {
        viewModelScope.launch {
            val response = myChallengeRepo.registerBankAccount(bankCode, bankAccount)
            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val success = it as Boolean
                    _accountRegistered.value = success
                    if (!success) {
                        setErrorData(result.errorCode, result.errorMessage)
                    }
                }
            }
        }
    }

    fun selectRewardFilter(filterNameEn: String) {
        viewModelScope.launch {
            val rewardFilter = RewardFilter.mock().copy()

            rewardFilter.let {
                it.selectRewardFilter = filterNameEn
                _rewardFilter.value = it
            }
        }
    }

    fun retrieveBankCodes() {
        viewModelScope.launch {
            val response = myChallengeRepo.retrieveBankCodes()
            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let { data ->
                    val bankList = data as List<DropDownItem>
                    _bankList.value = bankList
                    if (bankList.isEmpty()) {
                        setErrorData("", "?????? ?????? ?????? ??????")
                    }
                }
            }
        }
    }

    fun transferRewards(amount: Int) {
        viewModelScope.launch {
            val response = myChallengeRepo.transferRewards(amount)
            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val success = it as Boolean
                    _transferRewards.value = success
                    if (!success) {
                        setErrorData(result.errorCode, result.errorMessage)
                    }
                }
            }
        }
    }
}