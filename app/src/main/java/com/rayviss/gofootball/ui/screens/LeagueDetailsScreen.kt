package com.rayviss.gofootball.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rayviss.gofootball.data.models.allLeaguesResponse.Data
import com.rayviss.gofootball.ui.theme.Green200
import com.rayviss.gofootball.ui.theme.White50
import com.rayviss.gofootball.ui.viewmodels.MainViewModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LeagueDetailsScreen(
    navController: NavController,
    leagueId: String,
    viewModel: MainViewModel = hiltViewModel()
) {

    val leagueDetailsState = viewModel.leagueDetailsState.collectAsState().value
    val teamsState = viewModel.teamsState.collectAsState().value


    LaunchedEffect(key1 = leagueId) {
        viewModel.getLeagueById(leagueId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        when (leagueDetailsState) {
            is ScreenStateLeagueDetails.Empty -> Text(text = "No data available")

            is ScreenStateLeagueDetails.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading...")
                }
            }

            is ScreenStateLeagueDetails.Success -> {
                val league = leagueDetailsState.leaguesModel.data.first()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    GlideImage(
                        model = league.img,
                        contentDescription = "League Logo",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(text = league.name, style = MaterialTheme.typography.h5)
                    Text(text = league.continent.name)
                    Text(text = league.country.name)
                    Text(text = league.seasons.first().name)
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Teams",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    viewModel.getTeamsBySeasonId(league.id_current_season)
                    when (teamsState) {
                        is ScreenStateTeams.Empty -> Text(text = "No data available")

                        is ScreenStateTeams.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                                Text(text = "Loading...")
                            }
                        }

                        is ScreenStateTeams.Success -> {
                            val teams = teamsState.teamsModel.data
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {

                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    items(teams.size) {
                                        TeamItem(team = teams[it])
                                        Spacer(modifier = Modifier.height(12.dp))
                                    }
                                }
                            }
                        }

                        is ScreenStateTeams.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = teamsState.message)
                            }
                        }
                    }


                }
            }

            is ScreenStateLeagueDetails.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = leagueDetailsState.message)
                }
            }
        }
    }
}


@Composable
fun TeamItem(
    team: com.rayviss.gofootball.data.models.teamsResponse.Data,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(300.dp)
            .height(70.dp),
        backgroundColor = Color.White,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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