package biz.ohrae.challenge_screen.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge_repo.model.profile.NicknameState
import biz.ohrae.challenge_repo.model.register.ImageBucket
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.ui.main.ChallengeMainRepo
import biz.ohrae.challenge_repo.ui.main.UserRepo
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.ui.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeProfileViewModel @Inject constructor(
    private val registerRepo: ChallengeMainRepo,
    private val userRepo: UserRepo,
    private val prefs: SharedPreference,
    private val gson: Gson
) : BaseViewModel(prefs) {
    private val _user = MutableLiveData<User>()
    private val _profileImageUri = MutableLiveData<Uri?>(null)
    private val _nicknameState = MutableLiveData<NicknameState?>()
    private val _finishedUpdate = MutableLiveData(false)

    val user get() = _user
    val profileImageUri get() = _profileImageUri
    val nicknameState get() = _nicknameState
    val finishedUpdate get() = _finishedUpdate

    fun getUserInfo() {
        viewModelScope.launch {
            val response = userRepo.getUserData()
            response.flowOn(Dispatchers.IO).collect {
                it.data?.let { data ->
                    val user = data as User
                    _user.value = user
                    if (user.imageFile == null) {
                        _profileImageUri.value = null
                    } else {
                        _profileImageUri.value = Uri.parse(user.imageFile?.path)
                    }
                }
            }
        }
    }

    fun setProfileImage(uri: Uri) {
        viewModelScope.launch {
            _profileImageUri.value = uri
            uploadUserImage(uri.path.toString())
        }
    }

    fun uploadUserImage(imageFilePath: String) {
        viewModelScope.launch {
            val response = userRepo.uploadUserImage(imageFilePath)
            response.flowOn(Dispatchers.IO).collect { it ->
                it.data?.let { data ->
                    val imageBucket = data as ImageBucket
                    updateUserProfile(imageId = imageBucket.id)
                    isLoading(false)
                } ?: run {
                    isLoading(false)
                }
            }
        }
    }

    fun updateUserProfile(nickName: String? = null, imageId: Int? = null) {
        isLoading(true)
        viewModelScope.launch {
            val response = userRepo.updateUserProfile(nickName, imageId)
            response.flowOn(Dispatchers.IO).collect { it ->
                it.data?.let { data ->
                    val success = data as Boolean
                    if (success) {
                        if (nickName != null) {
                            _finishedUpdate.value = true
                        }
                    } else {
                        setErrorData(it.errorCode, it.errorMessage)
                    }
                } ?: run {
                    setErrorData(null, it.errorMessage)
                }
                isLoading(false)
            }
        }
    }

    fun checkNickName(nickName: String?) {
        viewModelScope.launch {
            val response = userRepo.checkNickname(nickName)
            response.flowOn(Dispatchers.IO).collect { it ->
                it.data?.let { data ->
                    val state = data as NicknameState
                    state.success = true
                    state.message = "사용가능한 닉네임입니다"

                    if (state.is_nickname_isolated != 1) {
                        state.success = false
                        state.message = "이미 사용중인 닉네임입니다."
                    }
                    if (state.is_nickname_valid != 1) {
                        state.success = false
                        state.message = "사용불가능한 닉네임입니다."
                    }
                    _nicknameState.value = state
                } ?: run {
                    setErrorData(null, it.errorMessage)
                }
                isLoading(false)
            }
        }
    }
}