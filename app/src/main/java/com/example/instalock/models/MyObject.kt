package com.example.instalock.models

import com.merakianalytics.orianna.types.core.summoner.Summoner

data class MyObject(
    var id: String,
    var name: String
) {
    companion object {
        fun convert(summoner: Summoner): MyObject {
            return MyObject(summoner.id, summoner.name)
        }
    }
}