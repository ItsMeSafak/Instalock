package com.example.instalock.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instalock.exceptions.LiveGameNotFound
import com.example.instalock.models.LiveGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LiveGameViewModel(application: Application) : MainViewModel(application) {

    private val _liveGameData: MutableLiveData<LiveGame> = MutableLiveData()
    val liveGameData: LiveData<LiveGame>
        get() = _liveGameData

    fun getLiveGame() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    _liveGameData.value = repository.getLiveGame()
                    _succes.value = true
                } catch (ex: LiveGameNotFound) {
                    _failed.value = ex.message
                }
            }
        }
    }
}