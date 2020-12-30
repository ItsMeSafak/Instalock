package com.example.instalock.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instalock.data.Repository
import com.example.instalock.exceptions.SummonerNotFound
import com.merakianalytics.orianna.types.common.Region
import com.merakianalytics.orianna.types.core.staticdata.Champion
import com.merakianalytics.orianna.types.core.summoner.Summoner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChampionViewModel(application: Application) : AndroidViewModel(application) {

    val repository = Repository()
    private val _championData: MutableLiveData<List<Champion>> = MutableLiveData()
    val championData: LiveData<List<Champion>>
        get() = _championData

    private var _succes: MutableLiveData<Boolean> = MutableLiveData()
    val succes: LiveData<Boolean>
        get() = _succes

    private var _failed: MutableLiveData<String> = MutableLiveData()
    val failed: LiveData<String>
        get() = _failed

    fun getChampions() {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                _championData.value = repository.getChampions()
                _succes.value = true
                //if (summonerData.value == null) throw SummonerNotFound("Couldn't find the summoner you were looking for")
            }
        }
    }
}