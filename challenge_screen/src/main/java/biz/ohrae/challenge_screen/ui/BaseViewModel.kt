package biz.ohrae.challenge_screen.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(private val prefs: SharedPreference): ViewModel() {
    private val _isLoading = MutableLiveData(false)

    val isLoading get() = _isLoading

    fun isLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun getUser(): User? {
        return prefs.getUserData()
    }
}