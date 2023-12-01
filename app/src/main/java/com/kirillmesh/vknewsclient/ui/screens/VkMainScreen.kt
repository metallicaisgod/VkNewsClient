package com.kirillmesh.vknewsclient.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kirillmesh.vknewsclient.ui.navigation.AppNavGraph
import com.kirillmesh.vknewsclient.ui.navigation.NavigationItem
import com.kirillmesh.vknewsclient.ui.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach { navigationItem ->

                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == navigationItem.screen.route
                    } ?: false

                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(navigationItem.screen.route)
                            }
                        },
                        icon = {
                            Icon(navigationItem.icon, contentDescription = null)
                        },
                        label = {
                            Text(stringResource(id = navigationItem.textRexId))
                        },
                        selectedContentColor = MaterialTheme.colors.onBackground,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }

            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navigationState.navHostController,
            newsFeedScreenNavigation = {
                FeedPostsScreen(
                    paddingValues = paddingValues
                ) {
                    navigationState.navigateToComments(it)
                }
            },
            commentsScreenNavigation = { feedPost ->
                CommentsScreen(
                    onBackPressedListener = { navigationState.navHostController.popBackStack() },
                    feedPost = feedPost
                )
                BackHandler {
                    navigationState.navHostController.popBackStack()
                }
            },
            favouriteScreenNavigation = { TextCounter("Favorite") },
            profileScreenNavigation = { TextCounter("Profile") }
        )
    }
}

@Composable
fun TextCounter(
    name: String,
) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name Count: $count",
        color = Color.Black
    )
}