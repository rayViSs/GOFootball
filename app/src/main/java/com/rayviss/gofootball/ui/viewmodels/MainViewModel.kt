package com.rayviss.gofootball.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayviss.gofootball.data.models.allLeaguesResponse.AllLeaguesModel
import com.rayviss.gofootball.data.models.leaguesResponse.LeaguesModel
import com.rayviss.gofootball.data.models.teamsResponse.TeamsModel
import com.rayviss.gofootball.domain.usecases.UseCase
import com.rayviss.gofootball.ui.screens.ScreenState
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

    private val _leaguesState = MutableStateFlow<ScreenState<AllLeaguesModel>>(ScreenState.Empty)
    val leaguesState: StateFlow<ScreenState<AllLeaguesModel>> = _leaguesState

    private val _leagueDetailsState = MutableStateFlow<ScreenState<LeaguesModel>>(ScreenState.Empty)
    val leagueDetailsState: StateFlow<ScreenState<LeaguesModel>> = _leagueDetailsState

    private val _teamsState = MutableStateFlow<ScreenState<TeamsModel>>(ScreenState.Empty)
    val teamsState: StateFlow<ScreenState<TeamsModel>> = _teamsState


    init {
        getAllLeagues()
    }


    private fun getAllLeagues() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val leagues = useCase.getAllLeaguesUseCase.invoke()
                _leaguesState.value = ScreenState.Success(leagues)
            } catch (e: Throwable) {
                Log.d("ERR", e.toString())
                _leaguesState.value = handleException(e)
            }
        }
    }

    fun getLeagueById(leagueId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val league = useCase.getLeaguesByIdsUseCase.invoke(leagueId)
                _leagueDetailsState.value = ScreenState.Success(league)
            } catch (e: Throwable) {
                Log.d("ERR", e.toString())
                _leagueDetailsState.value = handleException(e)
            }
        }
    }

    fun getTeamsBySeasonId(seasonId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val teams = useCase.getTeamsBySeasonIdUseCase.invoke(seasonId)
                _teamsState.value = ScreenState.Success(teams)
            } catch (e: Throwable) {
                _teamsState.value = handleException(e)
            }
        }
    }



    private fun handleException(throwable: Throwable): ScreenState<Nothing> {
        return when (throwable) {
            is HttpException, is IOException -> ScreenState.Error("Internet issue")
            else -> ScreenState.Error("Unknown error")
        }
    }
}