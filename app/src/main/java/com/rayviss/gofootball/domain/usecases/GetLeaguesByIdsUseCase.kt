package com.rayviss.gofootball.domain.usecases

import com.rayviss.gofootball.data.models.leaguesResponse.LeaguesModel
import com.rayviss.gofootball.domain.repository.FootballRepository

class GetLeaguesByIdsUseCase(private val repository: FootballRepository) {
    suspend operator fun invoke(ids: String): LeaguesModel {
        return repository.getLeaguesByIds(ids)
    }
}
