package com.kirillmesh.vknewsclient.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kirillmesh.vknewsclient.MainViewModel
import com.kirillmesh.vknewsclient.ui.navigation.AppNavGraph
import com.kirillmesh.vknewsclient.ui.navigation.rememberNavigationState

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach { navigationItem ->
                    BottomNavigationItem(
                        selected = currentRoute == navigationItem.screen.route,
                        onClick = { navigationState.navigateTo(navigationItem.screen.route) },
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
            homeScreenNavigation = {
                HomeScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues
                )
            },
            favouriteScreenNavigation = { TextCounter("Favorite") },
            profileScreenNavigation = { TextCounter("Profile") }
        )
    }
}

@Composable
fun TextCounter(
    name: String
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