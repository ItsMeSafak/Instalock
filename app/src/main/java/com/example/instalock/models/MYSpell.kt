package com.example.instalock.models

import com.merakianalytics.orianna.types.core.staticdata.ChampionSpell

data class MYSpell(
    val image: String,
    val name: String,
    val description: String
) {
    companion object {
        fun convertToMySpell(listOfSpells: List<ChampionSpell>): List<MYSpell> {
            val newList = arrayListOf<MYSpell>()
            for (spell in listOfSpells) {
                newList.add(MYSpell(spell.image.url, spell.name, spell.description))
            }
            return newList
        }
    }
}