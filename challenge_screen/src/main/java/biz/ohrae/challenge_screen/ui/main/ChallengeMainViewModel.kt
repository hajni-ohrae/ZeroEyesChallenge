package biz.ohrae.challenge_screen.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.model.main.FilterState
import biz.ohrae.challenge_screen.model.main.MainScreenState
import biz.ohrae.challenge_screen.model.user.UserChallengeListState
import biz.ohrae.challenge_screen.ui.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChallengeMainViewModel @Inject constructor(
    private val challengeMainRepo: ChallengeMainRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : BaseViewModel(prefs) {
    private val _mainScreenState = MutableLiveData<MainScreenState>()
    private val _userChallengeListState = MutableLiveData<UserChallengeListState>()
    private val _filterState = MutableLiveData<FilterState>()
    private val _tokenValid = MutableLiveData<Boolean>()
    private val _userData = MutableLiveData<User>()
    private val _challengeListPage = MutableLiveData(1)
    private val _userChallengeListPage = MutableLiveData(1)
    private val _isRefreshing = MutableLiveData(false)

    val filterState get() = _filterState
    val mainScreenState get() = _mainScreenState
    val tokenValid get() = _tokenValid
    val userChallengeListState get() = _userChallengeListState
    val userData get() = _userData
    val userChallengeListPage get() = _userChallengeListPage
    val challengeListPage get() = _challengeListPage

    val isRefreshing get() = _isRefreshing

    private fun login() {
        viewModelScope.launch {
            userRepo.login()
        }
    }

    fun userData() {
        viewModelScope.launch {
            _userData.value = prefs.getUserData()
        }
    }

    fun tokenCheck() {
        viewModelScope.launch {
            val response = userRepo.tokenCheck()
            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val success = it as Boolean
                    if (success) {
                        _tokenValid.value = true
                    } else {
                        if (result.errorCode == "2005" || result.errorCode == "2007") {
                            refreshToken()
                        } else {
                            _tokenValid.value = false
                        }
                    }
                }
            }
        }
    }

    private fun refreshToken() {
        viewModelScope.launch {
            val response = userRepo.refreshToken()
            response.flowOn(Dispatchers.IO).collect { result ->
                result.data?.let {
                    val success = it as Boolean
                    _tokenValid.value = success
                }
            }
        }
    }

    fun getChallengeList(
        paymentType: String = "",
        verificationPeriodType: String = "",
        per_week: String = "",
        period: String = "",
        is_like: String = "",
        age_limit_type: String = "",
        isInit: Boolean = false,
    ) {
        viewModelScope.launch {
            if (isInit) {
                _challengeListPage.value = 1
            }
            val page = _challengeListPage.value ?: 1

            val response = challengeMainRepo.getChallenges(
                paymentType,
                verificationPeriodType,
                per_week,
                period,
                age_limit_type,
                is_like,
                page
            )
            response.flowOn(Dispatchers.IO).collect {
                isLoading(false)
                _isRefreshing.value = false
                it.data?.let { data ->
                    val pager = it.pager

                    if (it.pager?.page == page) {
                        _challengeListPage.value = page + 1
                        Timber.d("current page : ${_challengeListPage.value}")
                    }

                    Timber.d("pager : ${gson.toJson(pager)}")
                    val topBannerList = MainScreenState.mock().topBannerList
                    val challengeList = data as MutableList<ChallengeData>
                    val state = mainScreenState.value?.copy()
                    state?.let { screenState ->
                        if (isInit) {
                            screenState.challengeList = challengeList
                        } else {
                            val list = mutableListOf<ChallengeData>()
                            screenState.challengeList?.addAll(challengeList)
                            screenState.challengePage = pager?.page
                        }
                        _mainScreenState.value = screenState
                    } ?: run {
                        _mainScreenState.value = MainScreenState(challengeList, topBannerList, challengePage = 1)
                    }
                } ?: run {
                    setErrorData(it.errorCode, it.errorMessage)
                }
            }
        }
    }
    fun selectUserFilter(filterNameEn: String) {
        viewModelScope.launch {
            val filterState = FilterState.mock().copy()

            filterState.let {
                it.selectUserChallengeType = filterNameEn
                _filterState.value = it
            }
        }
    }
    fun selectFilter(filterNameEn: String) {
        viewModelScope.launch {
            val filterState = FilterState.mock().copy()

            filterState.let {
                it.selectFilterType = filterNameEn
                _filterState.value = it
            }
        }
    }

    fun selectPeriodType(item: String) {
        viewModelScope.launch {
            val filterState = _filterState.value?.copy()

            filterState?.let {
                if (it.selectVerificationPeriodType == item) {
                    it.selectVerificationPeriodType = ""
                } else {
                    it.selectVerificationPeriodType = item
                }
                _filterState.value = it
            }
        }
    }

    fun selectPeriod(item: String) {
        viewModelScope.launch {
            val filterState = _filterState.value?.copy()

            filterState?.let {
                if (it.selectPeriod == item) {
                    it.selectPeriod = ""
                } else {
                    it.selectPeriod = item
                }
                _filterState.value = it
            }
        }
    }

    fun selectIsAdultOnly(item: String) {
        viewModelScope.launch {
            val filterState = _filterState.value?.copy()

            filterState?.let {
                if (it.selectIsAdultOnly == item) {
                    it.selectIsAdultOnly = ""
                } else {
                    it.selectIsAdultOnly = item
                }
                _filterState.value = it
            }
        }
    }

    fun getUserChallengeList(
        type: String,
        isInit: Boolean = false,
    ) {
        viewModelScope.launch {
            if (isInit) {
                _userChallengeListPage.value = 1
            }
            val page = _userChallengeListPage.value ?: 1
            val response = challengeMainRepo.getUserChallengeList(type)
            response.flowOn(Dispatchers.IO).collect {
                isLoading(false)
                _isRefreshing.value = false
                it.data?.let { data ->
                    val pager = it.pager

                    if (it.pager?.page == page) {
                        _userChallengeListPage.value = page + 1
                        Timber.d("current page : ${_userChallengeListPage.value}")
                    }
                    val userChallengeList = data as MutableList<ChallengeData>
                    val state = userChallengeListState.value?.copy()
                    state?.let {
                        if (isInit) {
                            state.userChallengeList = userChallengeList
                        } else {
                            state.userChallengeList?.addAll(userChallengeList)
                        }
                        _userChallengeListState.value = state
                    } ?: run {
                        _userChallengeListState.value = UserChallengeListState(userChallengeList)
                    }
                } ?: run {
                    setErrorData(it.errorCode, it.errorMessage)
                }
            }
        }
    }

    fun isRefreshing(isRefreshing: Boolean) {
        _isRefreshing.value = isRefreshing
    }
}