package com.rayviss.gofootball.domain.usecases

data class UseCase(
    val getAllLeaguesUseCase: GetAllLeaguesUseCase,
    val getLeaguesByIdsUseCase: GetLeaguesByIdsUseCase,
    val getTeamsBySeasonIdUseCase: GetTeamsBySeasonIdUseCase
)