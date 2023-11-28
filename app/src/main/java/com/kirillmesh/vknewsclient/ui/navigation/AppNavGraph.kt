package com.kirillmesh.vknewsclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kirillmesh.vknewsclient.domain.FeedPost

@Composable
fun AppNavGraph(
    navController: NavHostController,
    newsFeedScreenNavigation: @Composable () -> Unit,
    favouriteScreenNavigation: @Composable () -> Unit,
    profileScreenNavigation: @Composable () -> Unit,
    commentsScreenNavigation: @Composable (FeedPost) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        homeScreenNavGraph(
            newsFeedScreenNavigation = newsFeedScreenNavigation,
            commentsScreenNavigation = commentsScreenNavigation
        )

        composable(Screen.Favourite.route) {
            favouriteScreenNavigation()
        }
        composable(Screen.Profile.route) {
            profileScreenNavigation()
        }
    }
}