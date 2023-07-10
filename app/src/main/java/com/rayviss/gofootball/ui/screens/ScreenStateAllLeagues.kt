package com.rayviss.gofootball.ui.screens

import com.rayviss.gofootball.data.models.allLeaguesResponse.AllLeaguesModel
import com.rayviss.gofootball.data.models.allLeaguesResponse.Data
import com.rayviss.gofootball.data.models.leaguesResponse.LeaguesModel
import com.rayviss.gofootball.data.models.teamsResponse.TeamsModel

sealed class ScreenStateAllLeagues() {
    object Empty : ScreenStateAllLeagues()
    object Loading : ScreenStateAllLeagues()
    class Success(val allLeaguesModel: AllLeaguesModel) : ScreenStateAllLeagues()
    class Error(val message: String) : ScreenStateAllLeagues()
}

sealed class ScreenStateLeagueDetails() {
    object Empty : ScreenStateLeagueDetails()
    object Loading : ScreenStateLeagueDetails()
    class Success(val leaguesModel: LeaguesModel) : ScreenStateLeagueDetails()
    class Error(val message: String) : ScreenStateLeagueDetails()
}

sealed class ScreenStateTeams() {
    object Empty : ScreenStateTeams()
    object Loading : ScreenStateTeams()
    class Success(val teamsModel: TeamsModel) : ScreenStateTeams()
    class Error(val message: String) : ScreenStateTeams()
}