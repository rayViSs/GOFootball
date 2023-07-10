package com.rayviss.gofootball.domain.usecases

import com.rayviss.gofootball.data.models.allLeaguesResponse.AllLeaguesModel
import com.rayviss.gofootball.domain.repository.FootballRepository

class GetAllLeaguesUseCase(private val repository: FootballRepository) {
    suspend operator fun invoke(): AllLeaguesModel{
        return repository.getAllLeagues()
    }
}