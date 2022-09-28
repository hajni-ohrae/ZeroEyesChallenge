package biz.ohrae.challenge_screen.ui.participation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.ui.participation.ParticipationRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParticipationViewModel @Inject constructor(
    private val participationRepo: ParticipationRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : ViewModel() {
    private val _registerResult = MutableLiveData<FlowResult>()

    val registerResult get() = _registerResult

    fun registerChallenge(challengeData: ChallengeData) {
        viewModelScope.launch {
            val response = participationRepo.registerChallenge(challengeData, 0, 0, 0)

            response.flowOn(Dispatchers.IO).collect { result ->
                _registerResult.value = result
            }
        }
    }
}