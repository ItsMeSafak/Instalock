package com.example.instalock.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instalock.exceptions.ChampionDetailNotFound
import com.example.instalock.exceptions.ChampionsNotFound
import com.merakianalytics.orianna.types.core.staticdata.Champion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChampionViewModel(application: Application) : MainViewModel(application) {

    companion object {
        var currentName: String = "Aatrox"
    }

    private val _championData: MutableLiveData<List<Champion>> = MutableLiveData()
    val championData: LiveData<List<Champion>>
        get() = _championData

    private val _detailChampionData: MutableLiveData<Champion> = MutableLiveData()
    val detailChampionData: LiveData<Champion>
        get() = _detailChampionData

    fun getChampions() {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    _championData.value = repository.getChampions()
                } catch (ex: ChampionsNotFound) {
                    _failed.value = ex.message
                }
            }
        }
    }

    fun getDetailChampion(championName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    _detailChampionData.value = repository.getDetailChampion(championName)
                } catch (ex: ChampionDetailNotFound) {
                    _failed.value = ex.message
                }
            }
        }
    }
}