package com.example.instalock.models

import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery

data class MYMastery(
    val champion: MYChampion,
    val level: Int,
    val points: Int
) {
    companion object {
        fun convertToMyMastery(masteryList: List<ChampionMastery>): List<MYMastery> {
            val newList = arrayListOf<MYMastery>()
            for (mastery in masteryList) {
                newList.add(MYMastery(MYChampion.convertToMyChamp(mastery.champion), mastery.level, mastery.points))
            }
            return newList
        }
    }
}