package com.rayviss.gofootball.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rayviss.gofootball.R
import com.rayviss.gofootball.data.models.allLeaguesResponse.AllLeaguesModel
import com.rayviss.gofootball.data.models.allLeaguesResponse.Data
import com.rayviss.gofootball.navigation.Screen
import com.rayviss.gofootball.ui.theme.Green200
import com.rayviss.gofootball.ui.theme.Green700
import com.rayviss.gofootball.ui.theme.White10
import com.rayviss.gofootball.ui.theme.White50
import com.rayviss.gofootball.ui.viewmodels.MainViewModel


@Composable
fun LeaguesScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val leaguesState = viewModel.leaguesState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.leagues_title), color = White) },
                backgroundColor = Green700,
                actions = { ToWebScreen(navController) }
            )
        },
        content = {
            Modifier.padding(paddingValues = it)
            Box(
                modifier = Modifier
                    .background(White10)
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            {
                Column(modifier = Modifier.padding(20.dp)) {
                    when (leaguesState) {
                        is ScreenState.Empty -> Text(stringResource(R.string.no_data_available))
                        is ScreenState.Loading -> LoadingState()
                        is ScreenState.Error -> ErrorState(message = leaguesState.message)
                        is ScreenState.Success<AllLeaguesModel> -> {
                            ShowLeaguesList(
                                listLeague = leaguesState.data.data,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ShowLeaguesList(listLeague: List<Data>, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(listLeague.size) {
            LeagueItemCard(league = listLeague[it], onClickDetailLeague = { leagueId ->
                navController.navigate(
                    Screen.LeagueDetailsScreen.route.replace(
                        "{leagueId}",
                        leagueId
                    )
                )
            })
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun LeagueItemCard(
    league: Data,
    onClickDetailLeague: (String) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(300.dp)
            .height(175.dp)
            .clickable { onClickDetailLeague(league.id) },
        backgroundColor = Green200,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = league.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h4,
                color = White
            )
            Text(
                text = "${league.continent_name}/${league.country_name}",
                style = MaterialTheme.typography.h4,
                color = White50
            )
        }
    }
}

@Composable
fun ModifierBoxState(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun ErrorState(message: String) {
    ModifierBoxState {
        Text(text = message)
    }
}

@Composable
fun LoadingState() {
    ModifierBoxState {
        CircularProgressIndicator()
        Text(text = stringResource(id = R.string.loading))
    }
}

@Composable
fun ToWebScreen(navController: NavHostController) {
    IconButton(onClick = {
        navController.navigate(Screen.WebScreen.route)
    }) {
        Icon(
            imageVector = Icons.Filled.Search,
            tint = White,
            contentDescription = "web_view"
        )
    }
}

