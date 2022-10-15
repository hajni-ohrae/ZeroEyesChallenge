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
    private val _listPage = MutableLiveData(1)

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
        paymentType: String = "",
        verificationPeriodType: String = "",
        per_week: String = "",
        period: String = "",
        is_like:String = "",
        is_adult_only: String = "",
    ) {
        viewModelScope.launch {
            val page = listPage.value ?: 1

            val response = challengeMainRepo.getChallenges(
                paymentType,
                verificationPeriodType,
                per_week,
                period,
                is_adult_only,
                is_like,
                page
            )
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val pager = it.pager

                    if (it.pager?.page == page) {
                        _listPage.value = page + 1
                        Timber.e("current page : ${_listPage.value}")
                    }

                    Timber.e("pager : ${gson.toJson(pager)}")
                    val topBannerList = MainScreenState.mock().topBannerList
                    val challengeList = data as MutableList<ChallengeData>
                    val state = mainScreenState.value?.copy()
                    state?.let {
                        state.challengeList?.addAll(challengeList)
                        _mainScreenState.value = state
                    } ?: run {
                        _mainScreenState.value = MainScreenState(challengeList, topBannerList)
                    }
                } ?: run {

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