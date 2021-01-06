package com.example.instalock.data

import com.example.instalock.exceptions.*
import com.example.instalock.models.*
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
    suspend fun getSummoner(summonerName: String, regionName: String): MYSummoner {
        try {
            return withContext(Dispatchers.IO) {
                val summoner: Summoner? = Summoner.named(summonerName)
                    .withRegion(Region.values().filter { region -> region.tag == regionName }[0])
                    .get()
                if (summoner?.id != null) MYSummoner.convertToMySummoner(summoner) else
                    throw SummonerNotFound("Oops! We couldn't find the summoner you were looking for.")
            }
        } catch (ex: SummonerNotFound) {
            throw SummonerNotFound("Oops! We couldn't find the summoner you were looking for.")
        }
    }

    suspend fun getLiveGame(): LiveGame {
        try {
            return withContext(Dispatchers.IO) {
                val meAsSummoner: Summoner = Summoner.named(SummonerViewModel.summonerName!!)
                    .withRegion(SummonerViewModel.region)
                    .get()
                if (meAsSummoner.isInGame) {
                    val meAsPlayer =
                        CurrentMatch.forSummoner(meAsSummoner).get().participants.find {
                            it?.summoner?.id.equals(meAsSummoner.id)
                        }
                    val myTeam = meAsPlayer.team
                    val myTeamplayers = myTeam.participants.filter { it != meAsPlayer }
                    val enemyTeam: List<Player> = if (myTeam.side == Side.BLUE) {
                        CurrentMatch.forSummoner(meAsSummoner).get().redTeam.participants
                    } else {
                        CurrentMatch.forSummoner(meAsSummoner).get().blueTeam.participants
                    }
                    LiveGame(meAsPlayer.champion, myTeamplayers, enemyTeam)
                } else {
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

    suspend fun getMasteries(): List<MYMastery> {
        try {
            return withContext(Dispatchers.IO) {
                val meAsSummoner: Summoner = Summoner.named(SummonerViewModel.summonerName!!)
                    .withRegion(SummonerViewModel.region)
                    .get()
                val masteries = ChampionMasteries.forSummoner(meAsSummoner).get().take(LIMIT_CHAMPS)
                if (masteries.isNotEmpty()) MYMastery.convertToMyMastery(masteries)
                else throw MasteriesNotFound("You haven't mastered any champion! Weak...")
            }
        } catch (ex: MasteriesNotFound) {
            throw MasteriesNotFound("You haven't mastered any champion! Weak...")
        }
    }

    suspend fun getMatches(): List<MYMatch> {
        try {
            return withContext(Dispatchers.IO) {
                val meAsSummoner: Summoner = Summoner.named(SummonerViewModel.summonerName!!)
                    .withRegion(SummonerViewModel.region)
                    .get()
                val matches = meAsSummoner.matchHistory().get().take(LIMIT_MATCHES)
                if (matches.isNotEmpty()) MYMatch.convertToMyMatch(matches)
                else throw MatchesNotFound("You haven't played any matches lately.")
            }
        } catch (ex: MasteriesNotFound) {
            throw MatchesNotFound("You haven't played any matches lately.")
        }
    }

    suspend fun getChampions(): List<Champion> {
        try {
            return withContext(Dispatchers.IO) {
                val champions = Champions.withRegion(SummonerViewModel.region).get()
                if (champions.isNotEmpty()) champions else throw ChampionsNotFound("Something went wrong while fetching all champions.")
            }
        } catch (ex: ChampionsNotFound) {
            throw ChampionsNotFound("Something went wrong while fetching all champions.")
        }
    }

    suspend fun getDetailChampion(championName: String): Champion {
        try {
            return withContext(Dispatchers.IO) {
                val champion =
                    Champion.named(championName).withRegion(SummonerViewModel.region).get()
                champion
                    ?: throw ChampionDetailNotFound("Oh no... Couldn't find the champ you were looking for.")
            }
        } catch (ex: ChampionDetailNotFound) {
            throw ChampionDetailNotFound("Oh no... Couldn't find the champ you were looking for.")
        }
    }
}