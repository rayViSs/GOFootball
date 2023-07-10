package com.rayviss.gofootball.data.repository

import com.rayviss.gofootball.data.api.FootballApiService
import com.rayviss.gofootball.data.models.allLeaguesResponse.AllLeaguesModel
import com.rayviss.gofootball.data.models.leaguesResponse.LeaguesModel
import com.rayviss.gofootball.data.models.teamsResponse.TeamsModel
import com.rayviss.gofootball.domain.repository.FootballRepository
import javax.inject.Inject


class FootballRepositoryImpl @Inject constructor(
    private val api: FootballApiService
) : FootballRepository {

    override suspend fun getAllLeagues(): AllLeaguesModel {
        return api.getAllLeagues()
    }

    override suspend fun getLeaguesByIds(ids: String): LeaguesModel {
        return api.getLeaguesByIds(ids)
    }

    override suspend fun getTeamsBySeasonId(seasonId: String): TeamsModel {
        return  api.getTeamsBySeasonId(seasonId)
    }
}