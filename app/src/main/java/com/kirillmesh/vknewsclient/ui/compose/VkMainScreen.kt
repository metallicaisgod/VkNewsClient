package com.kirillmesh.vknewsclient.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val fabIsVisible = remember {
        mutableStateOf(true)
    }

    Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) {
                    Snackbar(
                            snackbarData = it,
                            actionColor = MaterialTheme.colorScheme.onSecondary,
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            floatingActionButton = {
                if (fabIsVisible.value) {
                    FloatingActionButton(
                            onClick = {
                                scope.launch {
                                    val action = snackbarHostState.showSnackbar(
                                            message = "This is snackbar",
                                            actionLabel = "Hide FAB",
                                            duration = SnackbarDuration.Long
                                    )
                                    fabIsVisible.value = action != SnackbarResult.ActionPerformed
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                    ) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            },

            bottomBar = {
                NavigationBar() {

                    val selectedItem = remember {
                        mutableStateOf(0)
                    }

                    val items = listOf(
                            NavigationItem.Home,
                            NavigationItem.Favorite,
                            NavigationItem.Profile
                    )
                    items.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                                selected = selectedItem.value == index,
                                onClick = { selectedItem.value = index },
                                icon = {
                                    Icon(navigationItem.icon, contentDescription = null)
                                },
                                label = {
                                    Text(stringResource(id = navigationItem.textRexId))
                                },
                                colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onBackground,
                                        selectedTextColor = MaterialTheme.colorScheme.onBackground,
                                        indicatorColor = MaterialTheme.colorScheme.background,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                                )

                        )
                    }

                }
            }
    ) {

    }
}