package com.example.instalock.data

import com.merakianalytics.orianna.types.common.Region
import com.merakianalytics.orianna.types.core.summoner.Summoner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    suspend fun getSummoner(summonerName: String, regionName: String): Summoner {
            return withContext(Dispatchers.IO) {
                Summoner.named(summonerName).withRegion(Region.values().filter { region -> region.tag == regionName}[0]).get()
            }
    }
}