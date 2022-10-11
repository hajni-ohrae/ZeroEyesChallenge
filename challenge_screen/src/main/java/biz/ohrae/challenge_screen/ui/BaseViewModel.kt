package biz.ohrae.challenge_screen.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(): ViewModel() {
    private val _isLoading = MutableLiveData(false)

    val isLoading get() = _isLoading

    fun isLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}