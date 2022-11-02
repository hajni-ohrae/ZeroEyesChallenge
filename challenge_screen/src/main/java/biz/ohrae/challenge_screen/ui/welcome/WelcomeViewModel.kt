package biz.ohrae.challenge_screen.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.model.WelcomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {
    private val _welcomeScreenState = MutableLiveData<WelcomeScreenState>()

    val welcomeScreenState: LiveData<WelcomeScreenState> get() = _welcomeScreenState

    init {
        _welcomeScreenState.value = WelcomeScreenState()
    }

    fun nextPage() {
        viewModelScope.launch {
            _welcomeScreenState.value = _welcomeScreenState.value?.copy()?.apply {
                if (currentPage + 1 < list.size) {
                    currentPage++
                }
            }
        }
    }

    fun setPage(page: Int) {
        viewModelScope.launch {
            _welcomeScreenState.value = _welcomeScreenState.value?.copy()?.apply {
                currentPage = page
            }
        }
    }
}