package biz.ohrae.challenge_screen.ui.detail

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.model.verify.VerifyListState
import biz.ohrae.challenge_repo.ui.detail.ChallengeDetailRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.detail.Verification
import biz.ohrae.challenge_screen.model.detail.VerificationState
import biz.ohrae.challenge_screen.model.user.UserChallengeListState
import biz.ohrae.challenge_screen.ui.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val repo: ChallengeDetailRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : BaseViewModel(prefs) {
    private val _challengeData = MutableLiveData<ChallengeData>()
    private val _challengeVerifiedList = MutableLiveData<List<ChallengeData>>()
    private val _verifyListState = MutableLiveData<VerifyListState>()
    private val _challengeVerificationState = MutableLiveData<VerificationState>()
    private val _challengers = MutableLiveData<List<User>>()
    private val _isJoined = MutableLiveData<Boolean>()
    private val _challengeAuthImageUri = MutableLiveData<Uri?>(null)
    private val _verified = MutableLiveData<Boolean?>(false)

    val challengeData get() = _challengeData
    val verifyListState get() = _verifyListState
    val challengers get() = _challengers
    val isJoined get() = _isJoined
    val challengeAuthImageUri get() = _challengeAuthImageUri
    val challengeVerificationState get() = _challengeVerificationState
    val challengeVerifiedList get() = _challengeVerifiedList
    val verified get() = _verified

    fun getChallenge(id: String) {
        viewModelScope.launch {
            repo.getChallenge(id).flowOn(Dispatchers.IO).collect { it ->
                Timber.e("getChallenge result : ${gson.toJson(it.data)}")
                if (it.data != null) {
                    val challengeData = it.data as ChallengeData
                    _challengeData.value = challengeData
                    val isJoined = !challengeData.inChallenge.isNullOrEmpty()
                    _isJoined.value = isJoined
                    if (isJoined) {
                        val verifications =
                            challengeData.inChallenge?.get(0)?.verifications ?: mutableListOf<Int>()
                        val totalVerificationCount = challengeData.total_verification_cnt
                        val total = (ceil(totalVerificationCount.toDouble() / 10f) * 10).toInt()

                        var successCount = 0
                        var failCount = 0
                        val today = challengeData.today ?: 0
                        val remainCount = challengeData.total_verification_cnt - today
                        val verificationList = mutableListOf<Verification>()
                        Timber.e("total : $total")

                        for (i in 0 until total) {
                            val verification = Verification(i + 1, Verification.NORMAL)
                            if (i < today) {
                                if (verifications.isEmpty()) {
                                    failCount++
                                    verification.state = Verification.FAIL
                                } else {
                                    verifications.forEach { day ->
                                        if (i == day) {
                                            successCount++
                                            verification.state = Verification.SUCCESS
                                        } else {
                                            failCount++
                                            verification.state = Verification.FAIL
                                        }
                                    }
                                }
                            } else if (i >= challengeData.total_verification_cnt) {
                                verification.state = Verification.HIDDEN
                            }
                            verificationList.add(verification)
                        }
                        Timber.e("verificationList : ${Gson().toJson(verificationList)}")
                        val state = VerificationState(
                            successCount,
                            remainCount,
                            failCount,
                            verificationList
                        )
                        _challengeVerificationState.value = state
                    }
                } else {
                    _challengeData.value = null
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

    fun getVerifyList(id: String) {
        viewModelScope.launch {
            repo.getVerifyList(id, "desc", "0").flowOn(Dispatchers.IO).collect {
                Timber.e("getVerifyList result : ${gson.toJson(it.data)}")
                if (it.data != null) {
                    val challengeVerifiedList = it.data as List<ChallengeData>
                    _challengeVerifiedList.value = challengeVerifiedList
                }
            }
        }
    }

    fun verifyChallenge(content: String, filePath: String) {
        viewModelScope.launch {
            challengeData.value?.let {
                val id = challengeData.value?.id.toString()
                val type = Utils.getAuthTypeEnglish(challengeData = it)

                repo.verifyChallenge(id, type, content, filePath).flowOn(Dispatchers.IO).collect { result ->
                    Timber.e("_verified result : ${gson.toJson(result.data)}")
                    if (result.data != null) {
                        _verified.value = result.data as Boolean
                    } else {
                        _verified.value = null
                    }
                    Timber.e("verify challenge Type : $type")
                }
            }
        }
    }

    fun setChallengeAuthImage(uri: Uri) {
        _challengeAuthImageUri.value = uri
    }

    fun getVerifyList(
        challengeId: String, order: String, isMine: String
    ) {
        viewModelScope.launch {
            repo.getVerifyList(challengeId,order,isMine).flowOn(Dispatchers.IO).collect(){
                if (it.data != null) {
                    val verifyListState =  it.data as List<VerifyData>
                    val state = VerifyListState(verifyListState)

                    _verifyListState.value = state
                }
            }
        }
    }
}