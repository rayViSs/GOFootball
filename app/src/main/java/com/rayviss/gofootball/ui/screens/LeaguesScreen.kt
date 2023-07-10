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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rayviss.gofootball.R
import com.rayviss.gofootball.data.models.allLeaguesResponse.Data
import com.rayviss.gofootball.navigation.Screen
import com.rayviss.gofootball.ui.theme.*
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
                title = {
                    Text(
                        text = stringResource(id = R.string.leagues_title),
                        color = White
                    )
                },
                backgroundColor = Green700,
                actions = {
                    ToWebScreen(navController)
                }
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
                        is ScreenStateAllLeagues.Empty -> Text(text = "No data available")

                        is ScreenStateAllLeagues.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                                Text(text = "Loading...")
                            }
                        }

                        is ScreenStateAllLeagues.Success -> {
                            ShowListWithLeagues(
                                listLeague = leaguesState.allLeaguesModel.data,
                                navController
                            )
                        }

                        is ScreenStateAllLeagues.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = leaguesState.message)
                            }
                        }
                    }
                }
            }
        }
    )

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun LeagueCard(league: com.rayviss.gofootball.data.models.allLeaguesResponse.Data) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Отображаем название лиги
                Text(text = league.name, style = TextStyle(fontSize = 18.sp))

                // Загружаем и отображаем логотип лиги с помощью Glide или Coil
                // Пример с Glide:
                GlideImage(
                    model = league.cc,
                    contentDescription = "Логотип лиги",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}


@Composable
fun LeagueItem(
    league: Data,
    onClickDetailLeague: (String) -> Unit
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
fun ShowListWithLeagues(listLeague: List<Data>, navController: NavHostController) {

    LazyColumn(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        items(listLeague.size) {
            LeagueItem(league = listLeague[it], onClickDetailLeague = { leagueId ->
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
