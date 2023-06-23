package com.kirillmesh.vknewsclient.ui.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kirillmesh.vknewsclient.domain.FeedPost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val feedPost = remember {
        mutableStateOf(FeedPost())
    }

    Scaffold(
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
        VkPostCard(
            modifier = Modifier.padding(8.dp),
            feedPost = feedPost.value
        ) { type ->
            val newStatistics =
                feedPost.value.statistics.toMutableList().apply {
                    replaceAll {
                        if (it.type == type) {
                            it.copy(count = it.count + 1)
                        } else {
                            it
                        }
                    }
                }
            feedPost.value = feedPost.value.copy(statistics = newStatistics)
        }
    }
}