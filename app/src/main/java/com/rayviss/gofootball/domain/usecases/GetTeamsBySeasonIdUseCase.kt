package com.rayviss.gofootball.domain.usecases

import com.rayviss.gofootball.data.models.teamsResponse.TeamsModel
import com.rayviss.gofootball.domain.repository.FootballRepository

class GetTeamsBySeasonIdUseCase(private val repository: FootballRepository) {
    suspend operator fun invoke(seasonId: String): TeamsModel{
        return repository.getTeamsBySeasonId(seasonId)
    }
}