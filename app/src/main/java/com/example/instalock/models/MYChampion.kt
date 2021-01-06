package com.example.instalock.models

import com.merakianalytics.orianna.types.core.staticdata.Champion

data class MYChampion(
    val name: String,
    val title: String,
    val imageURL: String,
    val iconURL: String,
    val lore: String,
    val ratings: Map<String, Int>,
    val spells: List<MYSpell>
) {
    companion object {
        fun convertToMyChamp(champion: Champion): MYChampion {
            val ratings = mapOf(
                "Physical" to champion.physicalRating,
                "Magic" to champion.magicRating,
                "Defence" to champion.defenseRating,
                "Difficulty" to champion.difficultyRating
            )
            return MYChampion(
                champion.name,
                champion.title,
                champion.skins[0].splashImageURL,
                champion.image.url,
                champion.lore,
                ratings,
                MYSpell.convertToMySpell(champion.spells)
            )
        }
    }
}