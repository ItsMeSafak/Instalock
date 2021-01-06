package com.example.instalock.models

import com.merakianalytics.orianna.types.core.match.Participant

data class MYPlayer(
    val champion: MYChampion,
    val summoner: MYSummoner
) {
    companion object {
        fun convertToMyPlayer(listOfPlayers: List<Participant>): List<MYPlayer> {
            val newList = arrayListOf<MYPlayer>()
            for (player in listOfPlayers) {
                newList.add(convertToSinglePlayer(player))
            }
            return newList
        }

        fun convertToSinglePlayer(player: Participant): MYPlayer {
            return MYPlayer(MYChampion.convertToMyChamp(player.champion),
                MYSummoner.convertToMySummoner(player.summoner))
        }
    }
}