package com.example.instalock.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instalock.data.Repository
import com.example.instalock.exceptions.SummonerNotFound
import com.merakianalytics.orianna.types.common.Region
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery
import com.merakianalytics.orianna.types.core.summoner.Summoner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SummonerViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var region: Region? = null
        var summonerName: String? = null
    }

    val repository = Repository()
    private val _summonerData: MutableLiveData<Summoner> = MutableLiveData()
    val summonerData: LiveData<Summoner>
        get() = _summonerData

    private val _masteryData: MutableLiveData<List<ChampionMastery>> = MutableLiveData()
    val masteryData: LiveData<List<ChampionMastery>>
        get() = _masteryData

    private var _succes: MutableLiveData<Boolean> = MutableLiveData()
    val succes: LiveData<Boolean>
        get() = _succes

    private var _failed: MutableLiveData<String> = MutableLiveData()
    val failed: LiveData<String>
        get() = _failed

    fun getSummoner(summonerName: String, regionName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                _summonerData.value = repository.getSummoner(summonerName, regionName)
                _succes.value = true
                if (summonerData.value == null) throw SummonerNotFound("Couldn't find the summoner you were looking for")
            }
        }
    }

    fun getMasteries(summoner: Summoner) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                _masteryData.value = repository.getMasteries(summoner)
                _succes.value = true
//                if (summonerData.value == null) throw SummonerNotFound("Couldn't find the summoner you were looking for")
            }
        }
    }
}