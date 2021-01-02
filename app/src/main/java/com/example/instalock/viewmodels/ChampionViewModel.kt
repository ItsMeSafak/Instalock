package com.example.instalock.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instalock.data.Repository
import com.example.instalock.exceptions.FetchingChampionsFailed
import com.merakianalytics.orianna.types.core.staticdata.Champion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChampionViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var currentName: String = "Aatrox"
    }

    val repository = Repository()
    private val _championData: MutableLiveData<List<Champion>> = MutableLiveData()
    val championData: LiveData<List<Champion>>
        get() = _championData

    private val _detailChampionData: MutableLiveData<Champion> = MutableLiveData()
    val detailChampionData: LiveData<Champion>
        get() = _detailChampionData

    private var _failed: MutableLiveData<String> = MutableLiveData()
    val failed: LiveData<String>
        get() = _failed

    fun getChampions() {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    _championData.value = repository.getChampions()
                } catch (ex: FetchingChampionsFailed) {
                    _failed.value = ex.message
                }
            }
        }
    }

    fun getDetailChampion(championName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                _detailChampionData.value = repository.getDetailChampion(championName)
            }
        }
    }
}