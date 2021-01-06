package com.example.instalock.models

import com.merakianalytics.orianna.types.common.Region
import com.merakianalytics.orianna.types.core.summoner.Summoner

data class MYSummoner(
    val id: String,
    val image: String,
    val name: String,
    val region: Region,
    val level: Int,
    val isInGame: Boolean
) {
    companion object {
        fun convertToMySummoner(summoner: Summoner): MYSummoner {
            return MYSummoner(summoner.id, summoner.profileIcon.image.url, summoner.name, summoner.region, summoner.level, summoner.isInGame)
        }
    }
}