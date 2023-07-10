package com.rayviss.gofootball.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayviss.gofootball.domain.usecases.UseCase
import com.rayviss.gofootball.ui.screens.ScreenStateAllLeagues
import com.rayviss.gofootball.ui.screens.ScreenStateLeagueDetails
import com.rayviss.gofootball.ui.screens.ScreenStateTeams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    private val _leaguesState = MutableStateFlow<ScreenStateAllLeagues>(ScreenStateAllLeagues.Empty)
    val leaguesState: StateFlow<ScreenStateAllLeagues> = _leaguesState

    private val _leagueDetailsState = MutableStateFlow<ScreenStateLeagueDetails>(ScreenStateLeagueDetails.Empty)
    val leagueDetailsState: StateFlow<ScreenStateLeagueDetails> = _leagueDetailsState

    private val _teamsState = MutableStateFlow<ScreenStateTeams>(ScreenStateTeams.Empty)
    val teamsState: StateFlow<ScreenStateTeams> = _teamsState

    init {
        getAllLeagues()
    }

    private fun getAllLeagues() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val leagues = useCase.getAllLeaguesUseCase.invoke()
                _leaguesState.value = ScreenStateAllLeagues.Success(leagues)
            } catch (e: HttpException) {
                _leaguesState.value = ScreenStateAllLeagues.Error("internet issue")
            } catch (e: IOException) {
                _leaguesState.value = ScreenStateAllLeagues.Error("something wrong")
            }
        }
    }

    fun getLeagueById(leagueId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val league = useCase.getLeaguesByIdsUseCase.invoke(leagueId)
                _leagueDetailsState.value = ScreenStateLeagueDetails.Success(league)
            } catch (e: HttpException) {
                _leagueDetailsState.value = ScreenStateLeagueDetails.Error("Internet issue")
            } catch (e: IOException) {
                _leagueDetailsState.value = ScreenStateLeagueDetails.Error("Something went wrong")
            }
        }
    }

    fun getTeamsBySeasonId(seasonId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val teams = useCase.getTeamsBySeasonIdUseCase.invoke(seasonId)
                _teamsState.value = ScreenStateTeams.Success(teams)
            } catch (e: HttpException) {
                _teamsState.value = ScreenStateTeams.Error("Internet issue")
            } catch (e: IOException) {
                _teamsState.value = ScreenStateTeams.Error("Something went wrong")
            }
        }
    }
}