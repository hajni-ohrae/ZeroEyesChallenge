package biz.ohrae.challenge_screen.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.model.card.ChallengeData
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChallengeMainViewModel @Inject constructor(private val challengeMainRepo: ChallengeMainRepo): ViewModel() {
    private val _challengeData = MutableLiveData<ChallengeData>()
    val challengeData get() = _challengeData

    fun getChallengeList() {
        viewModelScope.launch {
            challengeMainRepo.getChallenges().flowOn(Dispatchers.IO).collect {
                Timber.e("result : ${Gson().toJson(it.data)}")
                _challengeData.value = it.data as ChallengeData
            }
        }
    }
}