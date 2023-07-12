package com.rayviss.gofootball.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rayviss.gofootball.data.models.leaguesResponse.Data
import com.rayviss.gofootball.data.models.leaguesResponse.LeaguesModel
import com.rayviss.gofootball.data.models.teamsResponse.TeamsModel
import com.rayviss.gofootball.ui.viewmodels.MainViewModel

@Composable
fun LeagueDetailsScreen(
    leagueId: String,
    viewModel: MainViewModel = hiltViewModel()
) {
    val leagueDetailsState = viewModel.leagueDetailsState.collectAsState().value
    val teamsState = viewModel.teamsState.collectAsState().value


    LaunchedEffect(key1 = leagueId) {
        viewModel.getLeagueById(leagueId)
    }

    BoxWithConstraints(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        when (leagueDetailsState) {
            is ScreenState.Empty -> Text(text = "No data available")
            is ScreenState.Loading -> {
                LoadingContent()
            }
            is ScreenState.Error -> {
                ErrorContent(message = leagueDetailsState.message)
            }
            is ScreenState.Success<LeaguesModel> -> {
                val league = leagueDetailsState.data.data.first()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LeagueHeader(league = league)
                    Spacer(modifier = Modifier.height(20.dp))
                    TeamsSection(
                        leagueId = league.id_current_season,
                        viewModel = viewModel,
                        teamsState = teamsState
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LeagueHeader(league: Data) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = league.img,
                contentDescription = "League Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(end = 20.dp)
            )
        }
        Column {
            Text(text = league.name, style = MaterialTheme.typography.h5)
            Text(text = league.continent.name)
            Text(text = league.country.name)
            Text(text = league.seasons.first().name)
        }
    }
}

@Composable
fun TeamsSection(
    leagueId: String,
    viewModel: MainViewModel,
    teamsState: ScreenState<TeamsModel>
) {
    viewModel.getTeamsBySeasonId(leagueId)
    when (teamsState) {
        is ScreenState.Empty -> Text(text = "No data available")
        is ScreenState.Loading -> {
            LoadingContent()
        }
        is ScreenState.Error -> {
            ErrorContent(message = teamsState.message)
        }
        is ScreenState.Success<TeamsModel> -> {
            val teams = teamsState.data.data
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(teams) { team ->
                    TeamItem(team = team)
                }
            }
        }
    }
}

@Composable
fun TeamItem(team: com.rayviss.gofootball.data.models.teamsResponse.Data) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(300.dp)
            .height(70.dp),
        backgroundColor = Color.White,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = team.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5,
                color = Color.Black
            )
        }
    }
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text(text = "Loading...")
        }
    }
}

@Composable
fun ErrorContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}