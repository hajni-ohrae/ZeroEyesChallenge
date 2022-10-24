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
import biz.ohrae.challenge_screen.ui.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val _challengeVerifiedList = MutableLiveData<MutableList<VerifyData>>()
    private val _verifyListState = MutableLiveData<VerifyListState>()
    private val _challengeVerificationState = MutableLiveData<VerificationState>()
    private val _challengers = MutableLiveData<List<User>>()
    private val _isJoined = MutableLiveData<Boolean>()
    private val _isFinished = MutableLiveData<Boolean>()
    private val _challengeAuthImageUri = MutableLiveData<Uri?>(null)
    private val _verified = MutableLiveData<Boolean?>(false)
    private val _favorite = MutableLiveData<Boolean?>(false)
    private val _verifiedListPage = MutableLiveData(1)

    val challengeData get() = _challengeData
    val verifyListState get() = _verifyListState
    val challengers get() = _challengers
    val isJoined get() = _isJoined
    val isFinished get() = _isFinished
    val challengeAuthImageUri get() = _challengeAuthImageUri
    val challengeVerificationState get() = _challengeVerificationState
    val challengeVerifiedList get() = _challengeVerifiedList
    val verified get() = _verified
    val favorite get() = _favorite
    val verifiedListPage get() = _verifiedListPage

    fun getChallenge(id: String) {
        viewModelScope.launch {
            repo.getChallenge(id).flowOn(Dispatchers.IO).collect { it ->
                Timber.e("getChallenge result : ${gson.toJson(it.data)}")
                if (it.data != null) {
                    val challengeData = it.data as ChallengeData
                    _challengeData.value = challengeData
                    val isJoined =
                        !challengeData.inChallenge.isNullOrEmpty() && challengeData.status == "opened"
                    _isJoined.value = isJoined
                    val isFinished =
                        !challengeData.inChallenge.isNullOrEmpty() && challengeData.status == "finished"
                    _isJoined.value = isJoined
                    if (isJoined) {
                        val verifications = challengeData.inChallenge?.get(0)?.verifications
                        val totalVerificationCount = challengeData.total_verification_cnt
                        val total = (ceil(totalVerificationCount.toDouble() / 10f) * 10).toInt()

                        var successCount = 0
                        var failCount = 0
                        val today = challengeData.today ?: 0
                        val remainCount = challengeData.total_verification_cnt - today
                        val verificationList = mutableListOf<Verification>()
                        Timber.e("total : $total, today : $today")
                        Timber.e("verifications : ${Gson().toJson(verifications)}")

                        for (i in 0 until total) {
                            val verification = Verification(i + 1, Verification.NORMAL)
                            if (i <= today) {
                                if (verifications.isNullOrEmpty()) {
                                    failCount++
                                    verification.state = Verification.FAIL
                                } else {
                                    verifications.forEach { item ->
                                        Timber.e("verification : $i to ${item.day}")
                                        if (i == item.day) {
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

    fun getVerifyList(id: String, isInit: Boolean = false) {
        viewModelScope.launch {
            if (isInit) {
                _verifiedListPage.value = 1
            }
            val page = _verifiedListPage.value ?: 1

            repo.getVerifyList(id, "desc", "0", page).flowOn(Dispatchers.IO).collect {
                Timber.e("getVerifyList result : ${gson.toJson(it.data)}")
                if (it.data != null) {
                    val pager = it.pager

                    if (it.pager?.page == page) {
                        _verifiedListPage.value = page + 1
                    }

                    val challengeVerifiedList = it.data as MutableList<VerifyData>
                    if (isInit) {
                        _challengeVerifiedList.value = challengeVerifiedList
                    } else {
                        _challengeVerifiedList.value?.addAll(challengeVerifiedList)
                    }
                    isLoading(false)
                }
            }
        }
    }

    fun verifyChallenge(content: String, filePath: String) {
        viewModelScope.launch {
            challengeData.value?.let {
                val id = challengeData.value?.id.toString()
                val type = Utils.getAuthTypeEnglish(challengeData = it)

                repo.verifyChallenge(id, type, content, filePath).flowOn(Dispatchers.IO)
                    .collect { result ->
                        Timber.e("_verified result : ${gson.toJson(result.data)}")
                        if (result.data != null) {
                            val success = result.data as Boolean
                            _verified.value = success
                            if (!success) {
                                setErrorData(result.errorCode, result.errorMessage)
                            }
                        } else {
                            _verified.value = null
                            setErrorData(null, result.errorMessage)
                        }
                    }
            }
        }
    }

    fun favoriteChallenge(challengeId: String, like: Int) {
        viewModelScope.launch {
            challengeData.value?.let {
                repo.favoriteChallenge(challengeId, like).flowOn(Dispatchers.IO)
                    .collect { result ->
                        if (result.data != null) {
                            val success = result.data as Boolean
                            _favorite.value = success
                            if (!success) {
                                setErrorData(result.errorCode, result.errorMessage)
                            }
                        } else {
                            _favorite.value = null
                            setErrorData(null, result.errorMessage)
                        }
                    }
            }
        }
    }

    fun setChallengeAuthImage(uri: Uri) {
        _challengeAuthImageUri.value = uri
    }
}