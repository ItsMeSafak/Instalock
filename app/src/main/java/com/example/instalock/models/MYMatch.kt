package com.example.instalock.models

import android.util.Log
import com.merakianalytics.orianna.types.core.match.Match

data class MYMatch(
    val player: MYPlayer,
    val championPlayed: MYChampion,
    val mode: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val isWinner: Boolean
) {
    companion object {
        fun convertToMyMatch(listOfMatches: List<Match>): List<MYMatch> {
            val newList = arrayListOf<MYMatch>()
            for (match in listOfMatches) {
                newList.add(convertToSingleMatch(match))
            }
            return newList
        }

        private fun convertToSingleMatch(match: Match): MYMatch {
            val thisParticipant = MYPlayer.convertToSinglePlayer(match.participants[0])
            Log.d("pog", match.toJSON())
            return MYMatch(
                thisParticipant,
                thisParticipant.champion,
                match.mode.name,
                match.participants[0].stats.kills,
                match.participants[0].stats.deaths,
                match.participants[0].stats.assists,
                match.participants[0].stats.isWinner
            )
        }
    }
}