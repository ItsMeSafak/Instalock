package com.example.instalock.data

import android.util.Log
import com.example.instalock.exceptions.*
import com.example.instalock.models.LiveGame
import com.example.instalock.models.MyObject
import com.example.instalock.viewmodels.SummonerViewModel
import com.merakianalytics.orianna.types.common.Region
import com.merakianalytics.orianna.types.common.Side
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery
import com.merakianalytics.orianna.types.core.match.Match
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch
import com.merakianalytics.orianna.types.core.spectator.Player
import com.merakianalytics.orianna.types.core.staticdata.Champion
import com.merakianalytics.orianna.types.core.staticdata.Champions
import com.merakianalytics.orianna.types.core.summoner.Summoner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val LIMIT_MATCHES = 10
const val LIMIT_CHAMPS = 5

class Repository {
    suspend fun getSummoner(summonerName: String, regionName: String): Summoner {
        try {
            return withContext(Dispatchers.IO) {
                val summoner: Summoner? = Summoner.named(summonerName)
                    .withRegion(Region.values().filter { region -> region.tag == regionName }[0])
                    .get()
                if (summoner?.id != null) summoner else throw SummonerNotFound("Oops! We couldn't find the summoner you were looking for.")
            }
        } catch (ex: SummonerNotFound) {
            throw SummonerNotFound("Oops! We couldn't find the summoner you were looking for.")
        }
    }

    suspend fun getLiveGame(): LiveGame {
        try {
            return withContext(Dispatchers.IO) {
                val meAsSummoner: Summoner = getSummoner(
                    SummonerViewModel.summonerName!!,
                    SummonerViewModel.region?.tag!!
                )
                if (meAsSummoner.isInGame) {
                    val meAsPlayer =
                        CurrentMatch.forSummoner(meAsSummoner).get().participants.find {
                            it?.summoner?.id.equals(meAsSummoner.id)
                        }
                    val myTeam = meAsPlayer.team
                    val myTeamplayers = myTeam.participants
                    val enemyTeam: List<Player> = if (myTeam.side == Side.BLUE) {
                        CurrentMatch.forSummoner(meAsSummoner).get().redTeam.participants
                    } else {
                        CurrentMatch.forSummoner(meAsSummoner).get().blueTeam.participants
                    }
                    LiveGame(meAsPlayer.champion, myTeamplayers, enemyTeam)
                } else{
                    throw LiveGameNotFound("It seems that youre not in a game.")
                }
            }
        } catch (ex: LiveGameNotFound) {
            throw LiveGameNotFound("It seems that youre not in a game.")
        }
    }

    suspend fun getSummoner2(summonerName: String, regionName: String): MyObject {
        try {
            return withContext(Dispatchers.IO) {
                val summoner: Summoner? = Summoner.named(summonerName)
                    .withRegion(Region.values().filter { region -> region.tag == regionName }[0])
                    .get()
                if (summoner?.id != null) MyObject(
                    summoner.id,
                    summoner.name
                ) else throw SummonerNotFound("Oops! We couldn't find the summoner you were looking for.")
            }
        } catch (ex: SummonerNotFound) {
            throw SummonerNotFound("Oops! We couldn't find the summoner you were looking for.")
        }
    }

    suspend fun getMasteries(summoner: Summoner): List<ChampionMastery> {
        return withContext(Dispatchers.IO) {
            ChampionMasteries.forSummoner(summoner).get().take(LIMIT_CHAMPS)
        }
    }

    suspend fun getMatches(summoner: Summoner): List<Match> {
        return withContext(Dispatchers.IO) {
            summoner.matchHistory().get().take(LIMIT_MATCHES)
        }
    }

    suspend fun getChampions(): List<Champion> {
        try {
            return withContext(Dispatchers.IO) {
                Champions
                    .withRegion(SummonerViewModel.region).get()
            }
        } catch (ex: FetchingChampionsFailed) {
            throw FetchingChampionsFailed("Something went wrong while fetching all champions")
        }
    }

    suspend fun getDetailChampion(championName: String): Champion {
        return withContext(Dispatchers.IO) {
            Champion.named(championName).withRegion(SummonerViewModel.region).get()
        }
    }
}