package biz.ohrae.challenge_screen.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.model.MainScreenState
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
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

    fun getChallengeList() {
        viewModelScope.launch {
            val response = challengeMainRepo.getChallenges().flowOn(Dispatchers.IO).collect {
                Timber.e("result : ${gson.toJson(it.data)}")
                _mainScreenState.value = it.data as MainScreenState
            }
        }
    }
}