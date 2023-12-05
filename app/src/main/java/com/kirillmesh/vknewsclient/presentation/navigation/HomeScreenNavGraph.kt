package com.kirillmesh.vknewsclient.presentation.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kirillmesh.vknewsclient.domain.entities.FeedPost

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenNavigation: @Composable () -> Unit,
    commentsScreenNavigation: @Composable (FeedPost) -> Unit,
) {

    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenNavigation()
        }
        composable(
            Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST) {
                    type = FeedPost.NavigationType
                }
            )
        ) {
            val feedPost = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                @Suppress("DEPRECATION")
                it.arguments?.getParcelable(Screen.KEY_FEED_POST)
            } else {
                it.arguments?.getParcelable(Screen.KEY_FEED_POST, FeedPost::class.java)
            } ?: throw java.lang.RuntimeException("Arg is null")

            commentsScreenNavigation(feedPost)
        }
    }
}