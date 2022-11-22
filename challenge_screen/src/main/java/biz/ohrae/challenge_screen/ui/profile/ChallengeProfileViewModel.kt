package biz.ohrae.challenge_screen.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    val user get() = _user
    val profileImageUri get() = _profileImageUri

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
                    isLoading(false)
                } ?: run {
                    isLoading(false)
                }
            }
        }
    }
}