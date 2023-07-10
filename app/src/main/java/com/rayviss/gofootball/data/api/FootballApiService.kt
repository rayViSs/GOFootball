package com.rayviss.gofootball.data.api

import com.rayviss.gofootball.data.models.allLeaguesResponse.AllLeaguesModel
import com.rayviss.gofootball.data.models.leaguesResponse.LeaguesModel
import com.rayviss.gofootball.data.models.teamsResponse.TeamsModel
import com.rayviss.gofootball.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApiService {
    @GET("leagues/?user=${Constants.USERNAME_API}&token=${Constants.TOKEN_API}&t=list")
    suspend fun getAllLeagues(): AllLeaguesModel

    @GET("leagues/?user=${Constants.USERNAME_API}&token=${Constants.TOKEN_API}&t=sort")
    suspend fun getLeaguesByIds(
        @Query("ids") ids: String
    ): LeaguesModel

    @GET("teams/?user=${Constants.USERNAME_API}&token=${Constants.TOKEN_API}&t=byseason")
    suspend fun getTeamsBySeasonId(
        @Query("season_id") seasonId: String
    ): TeamsModel
}

//class FootballApiDataSource(private val apiService: FootballApiService) {
//    suspend fun getAllLeagues(user: String, token: String): Response<AllLeaguesResponse> {
//        return apiService.getAllLeagues(user, token, "list")
//    }
//
//    suspend fun getLeaguesByIds(user: String, token: String, ids: String): Response<LeaguesResponse> {
//        return apiService.getLeaguesByIds(user, token, "sort", ids)
//    }
//
//    suspend fun getTeamsBySeasonId(user: String, token: String, seasonId: String): Response<TeamsResponse> {
//        return apiService.getTeamsBySeasonId(user, token, "byseason", seasonId)
//    }
//}