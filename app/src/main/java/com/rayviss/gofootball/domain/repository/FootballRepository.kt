package com.rayviss.gofootball.domain.repository

import com.rayviss.gofootball.data.models.allLeaguesResponse.AllLeaguesModel
import com.rayviss.gofootball.data.models.leaguesResponse.LeaguesModel
import com.rayviss.gofootball.data.models.teamsResponse.TeamsModel

interface FootballRepository {

    suspend fun getAllLeagues(): AllLeaguesModel
    suspend fun getLeaguesByIds(ids: String): LeaguesModel
    suspend fun getTeamsBySeasonId(seasonId: String): TeamsModel
}