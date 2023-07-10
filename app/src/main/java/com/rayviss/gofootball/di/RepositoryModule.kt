package com.rayviss.gofootball.di

import com.rayviss.gofootball.data.api.FootballApiService
import com.rayviss.gofootball.data.repository.FootballRepositoryImpl
import com.rayviss.gofootball.domain.repository.FootballRepository
import com.rayviss.gofootball.domain.usecases.GetAllLeaguesUseCase
import com.rayviss.gofootball.domain.usecases.GetLeaguesByIdsUseCase
import com.rayviss.gofootball.domain.usecases.GetTeamsBySeasonIdUseCase
import com.rayviss.gofootball.domain.usecases.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMatchRepository(footballApiService: FootballApiService): FootballRepository {
        return FootballRepositoryImpl(footballApiService)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: FootballRepository): UseCase =
        UseCase(
            getAllLeaguesUseCase = GetAllLeaguesUseCase(repository),
            getLeaguesByIdsUseCase = GetLeaguesByIdsUseCase(repository),
            getTeamsBySeasonIdUseCase = GetTeamsBySeasonIdUseCase(repository)
        )
}