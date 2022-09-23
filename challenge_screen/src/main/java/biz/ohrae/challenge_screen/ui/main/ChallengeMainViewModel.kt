package biz.ohrae.challenge_screen.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.model.MainScreenState
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
) : ViewModel() {
    private val _mainScreenState = MutableLiveData<MainScreenState>()
    val mainScreenState get() = _mainScreenState

    init {
        login()
    }

    private fun login() {
        viewModelScope.launch {
            userRepo.login()
        }
    }

    fun getChallengeList(paymentType: String = "", verificationPeriodType: String = "") {
        viewModelScope.launch {
            val response = challengeMainRepo.getChallenges(paymentType, verificationPeriodType)
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

//    fun getChallengeList() {
//        viewModelScope.launch {
//            challengeMainRepo.getChallenges().flowOn(Dispatchers.IO).collect {
//                Timber.e("result : ${gson.toJson(it.data)}")
//                it.data?.let { data ->
//                    val state = data as MainScreenState
//                    _mainScreenState.value = state
//                }
//            }
//        }
//    }
}