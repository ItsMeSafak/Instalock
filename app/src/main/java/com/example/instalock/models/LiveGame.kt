package com.example.instalock.models

import com.merakianalytics.orianna.types.core.spectator.Player
import com.merakianalytics.orianna.types.core.staticdata.Champion

data class LiveGame (
    var yourchampion: Champion,
    var yourTeam: List<Player>,
    var enemyTeam: List<Player>
)