package com.kirillmesh.vknewsclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navController: NavHostController,
    homeScreenNavigation: @Composable () -> Unit,
    favouriteScreenNavigation: @Composable () -> Unit,
    profileScreenNavigation: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NewsFeed.route
    ) {
        composable(Screen.NewsFeed.route) {
            homeScreenNavigation()
        }
        composable(Screen.Favourite.route) {
            favouriteScreenNavigation()
        }
        composable(Screen.Profile.route) {
            profileScreenNavigation()
        }
    }
}