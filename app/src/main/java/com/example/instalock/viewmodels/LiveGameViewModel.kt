package com.example.instalock.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instalock.data.Repository
import com.example.instalock.exceptions.LiveGameNotFound
import com.example.instalock.models.LiveGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LiveGameViewModel(application: Application) : AndroidViewModel(application) {

    val repository = Repository()
    private val _liveGameData: MutableLiveData<LiveGame> = MutableLiveData()
    val liveGameData: LiveData<LiveGame>
        get() = _liveGameData

    private var _succes: MutableLiveData<Boolean> = MutableLiveData()
    val succes: LiveData<Boolean>
        get() = _succes

    private var _failed: MutableLiveData<String> = MutableLiveData()
    val failed: LiveData<String>
        get() = _failed

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