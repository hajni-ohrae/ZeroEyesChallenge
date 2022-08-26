package biz.ohrae.challenge_screen.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.model.card.ChallengeData
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
    private val _challengeData = MutableLiveData<ChallengeData>()
    val challengeData get() = _challengeData

    init {
        login()
    }

    private fun login() {
        viewModelScope.launch {
            if (prefs.getUserData() == null) {
                userRepo.login()
            }
        }
    }

    fun getChallengeList() {
        viewModelScope.launch {
            challengeMainRepo.getChallenges().flowOn(Dispatchers.IO).collect {
                Timber.e("result : ${gson.toJson(it.data)}")
                _challengeData.value = it.data as ChallengeData
            }
        }
    }
}