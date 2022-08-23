package biz.ohrae.challenge_screen.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.ohrae.challenge.model.card.ChallengeData
import biz.ohrae.challenge.model.list_item.ChallengeItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeMainViewModel @Inject constructor(): ViewModel() {
    private val _challengeItemList = MutableLiveData<List<ChallengeData>>()
    val challengeItemList get() = _challengeItemList
    fun getChallengeList() {
        viewModelScope.launch {
            _challengeItemList.value = listOf(ChallengeData.mock())
        }
    }

}