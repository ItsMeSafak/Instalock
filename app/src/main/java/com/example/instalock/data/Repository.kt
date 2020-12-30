package com.example.instalock.data

import com.example.instalock.viewmodels.SummonerViewModel
import com.merakianalytics.orianna.types.common.Region
import com.merakianalytics.orianna.types.core.staticdata.Champion
import com.merakianalytics.orianna.types.core.staticdata.Champions
import com.merakianalytics.orianna.types.core.summoner.Summoner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    suspend fun getSummoner(summonerName: String, regionName: String): Summoner {
        return withContext(Dispatchers.IO) {
            Summoner.named(summonerName)
                .withRegion(Region.values().filter { region -> region.tag == regionName }[0]).get()
        }
    }

    suspend fun getChampions(): List<Champion> {
        return withContext(Dispatchers.IO) {
            Champions
                .withRegion(SummonerViewModel.region).get()
        }
    }
}