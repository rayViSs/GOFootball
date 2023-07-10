package com.rayviss.gofootball.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LeaguesScreen : Screen("leagues_screen")
    object LeagueDetailsScreen : Screen("league_details_screen/{leagueId}")
    object WebScreen : Screen("web_screen")
}

