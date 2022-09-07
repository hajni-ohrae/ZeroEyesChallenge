package biz.ohrae.challenge_screen.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge_repo.ui.detail.ChallengeDetailRepo
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
class ChallengeDetailViewModel @Inject constructor(
    private val repo: ChallengeDetailRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : ViewModel() {
    private val _challengeData = MutableLiveData<ChallengeData>()

    val challengeData get() = _challengeData

    fun getChallenge(id: String) {
        viewModelScope.launch {
            repo.getChallenge(id).flowOn(Dispatchers.IO).collect {
                Timber.e("result : ${gson.toJson(it.data)}")
                if (it.data != null) {
                    _challengeData.value = it.data as ChallengeData
                }
            }
        }
    }
}