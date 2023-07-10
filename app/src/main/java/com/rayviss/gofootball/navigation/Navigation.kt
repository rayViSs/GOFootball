package com.rayviss.gofootball.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rayviss.gofootball.ui.screens.LeagueDetailsScreen
import com.rayviss.gofootball.ui.screens.LeaguesScreen
import com.rayviss.gofootball.ui.screens.SplashScreen
import com.rayviss.gofootball.ui.screens.WebScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.LeaguesScreen.route) {
            LeaguesScreen(navController)
        }
        composable(Screen.LeagueDetailsScreen.route) { backStackEntry ->
            val leagueId = backStackEntry.arguments?.getString("leagueId")
            if (leagueId != null) {
                LeagueDetailsScreen(navController, leagueId = leagueId)
            }
        }
        composable(Screen.WebScreen.route) {
            WebScreen()
        }
    }
}
