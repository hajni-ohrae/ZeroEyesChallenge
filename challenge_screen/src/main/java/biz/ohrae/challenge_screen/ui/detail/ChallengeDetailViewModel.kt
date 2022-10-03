package biz.ohrae.challenge_screen.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
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
    private val _challengers = MutableLiveData<List<User>>()
    private val _isJoined = MutableLiveData<Boolean>()

    val challengeData get() = _challengeData
    val challengers get() = _challengers
    val isJoined get() = _isJoined

    fun getChallenge(id: String) {
        viewModelScope.launch {
            repo.getChallenge(id).flowOn(Dispatchers.IO).collect {
                Timber.e("getChallenge result : ${gson.toJson(it.data)}")
                if (it.data != null) {
                    val challengeData = it.data as ChallengeData
                    _challengeData.value = challengeData
                    _isJoined.value = challengeData.inChallenge.isNullOrEmpty() == false
                }
            }
        }
    }

    fun getUserByChallenge(id: String, page: Int = 1, count: Int = 10) {
        viewModelScope.launch {
            repo.getUserByChallenge(id, page, count).flowOn(Dispatchers.IO).collect {
                Timber.e("getUserByChallenge result : ${gson.toJson(it.data)}")
                if (it.data != null) {
                    val challengers = it.data as List<User>
                    _challengers.value = challengers
                }
            }
        }
    }
}