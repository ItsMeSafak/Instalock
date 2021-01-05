package com.example.instalock.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instalock.exceptions.MasteriesNotFound
import com.example.instalock.exceptions.MatchesNotFound
import com.example.instalock.exceptions.SummonerNotFound
import com.example.instalock.models.MyObject
import com.merakianalytics.orianna.types.common.Region
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery
import com.merakianalytics.orianna.types.core.match.Match
import com.merakianalytics.orianna.types.core.summoner.Summoner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SummonerViewModel(application: Application) : MainViewModel(application) {

    companion object {
        var region: Region? = null
        var summonerName: String? = null
        var summonerId: String? = null
    }

    private val _summonerData: MutableLiveData<Summoner> = MutableLiveData()
    val summonerData: LiveData<Summoner>
        get() = _summonerData

    private val _summonerData2: MutableLiveData<MyObject> = MutableLiveData()
    val summonerData2: LiveData<MyObject>
        get() = _summonerData2

    private val _masteryData: MutableLiveData<List<ChampionMastery>> = MutableLiveData()
    val masteryData: LiveData<List<ChampionMastery>>
        get() = _masteryData

    private val _matchData: MutableLiveData<List<Match>> = MutableLiveData()
    val matchData: LiveData<List<Match>>
        get() = _matchData

    fun getSummoner(summonerName: String, regionName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    _summonerData.value = repository.getSummoner(summonerName, regionName)
                    _succes.value = true
                } catch (ex: SummonerNotFound) {
                    _failed.value = ex.message
                }
            }
        }
    }

    fun getSummoner2(summonerName: String, regionName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    _summonerData2.value = repository.getSummoner2(summonerName, regionName)
                } catch (ex: SummonerNotFound) {
                    _failed.value = ex.message
                }
            }
        }
    }

    fun getMasteries(summoner: Summoner) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    _masteryData.value = repository.getMasteries(summoner)
                } catch (ex: MasteriesNotFound) {
                    _failed.value = ex.message
                }
            }
        }
    }

    fun getMatches(summoner: Summoner) {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try {
                    _matchData.value = repository.getMatches(summoner)
                } catch (ex: MatchesNotFound) {
                    _failed.value = ex.message
                }
            }
        }
    }
}