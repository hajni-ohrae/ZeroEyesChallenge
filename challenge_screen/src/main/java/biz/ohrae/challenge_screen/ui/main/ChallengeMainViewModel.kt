package biz.ohrae.challenge_screen.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.model.main.FilterState
import biz.ohrae.challenge_screen.model.main.MainScreenState
import biz.ohrae.challenge_screen.model.user.UserChallengeListState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeMainViewModel @Inject constructor(
    private val challengeMainRepo: ChallengeMainRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : ViewModel() {
    private val _mainScreenState = MutableLiveData<MainScreenState>()
    private val _userChallengeListState = MutableLiveData<UserChallengeListState>()
    private val _filterState = MutableLiveData<FilterState>()
    private val _tokenValid = MutableLiveData<Boolean>()
    private val _userData = MutableLiveData<User>()
    private val _listPage = MutableLiveData(0)

    val filterState get() = _filterState
    val mainScreenState get() = _mainScreenState
    val tokenValid get() = _tokenValid
    val userChallengeListState get() = _userChallengeListState
    val userData get() = _userData
    val listPage get() = _listPage

    init {
//        login()
        userData()
        tokenCheck()
        selectFilter("all")
    }

    private fun login() {
        viewModelScope.launch {
            userRepo.login()
        }
    }

    private fun userData() {
        viewModelScope.launch {
            _userData.value = prefs.getUserData()
        }
    }

    private fun tokenCheck() {
        viewModelScope.launch {
            val response = userRepo.tokenCheck()
            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val success = it as Boolean
                    _tokenValid.value = success
                }
            }
        }
    }

    fun getChallengeList(
        paymentType: String,
        verificationPeriodType: String,
        per_week: String,
        period: String,
        is_adult_only: String,
    ) {
        viewModelScope.launch {
            val page = listPage.value ?: 0

            val response = challengeMainRepo.getChallenges(
                paymentType,
                verificationPeriodType,
                per_week,
                period,
                is_adult_only,
                page
            )
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val topBannerList = MainScreenState.mock().topBannerList
                    val challengeList = data as List<ChallengeData>
                    val state = MainScreenState(challengeList, topBannerList)
                    _mainScreenState.value = state
                }
            }
        }
    }

    fun selectFilter(filterNameEn: String) {
        viewModelScope.launch {
            val filterState = FilterState.mock().copy()

            filterState?.let {
                it.selectFilterType = filterNameEn
                _filterState.value = it
            }
        }
    }

    fun selectPeriodType(item: String) {
        viewModelScope.launch {
            val filterState = _filterState.value?.copy()

            filterState?.let {
                it.selectVerificationPeriodType = item
                _filterState.value = it
            }
        }
    }

    fun selectPeriod(item: String) {
        viewModelScope.launch {
            val filterState = _filterState.value?.copy()

            filterState?.let {
                it.selectPeriod = item
                _filterState.value = it
            }
        }
    }

    fun selectIsAdultOnly(item: String) {
        viewModelScope.launch {
            val filterState = _filterState.value?.copy()

            filterState?.let {
                it.selectIsAdultOnly = item
                _filterState.value = it
            }
        }
    }

    fun getUserChallengeList() {
        viewModelScope.launch {
            val response = challengeMainRepo.getUserChallengeList()
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val userChallengeList = data as List<ChallengeData>
                    val state = UserChallengeListState(userChallengeList)
                    _userChallengeListState.value = state
                }
            }
        }
    }
}