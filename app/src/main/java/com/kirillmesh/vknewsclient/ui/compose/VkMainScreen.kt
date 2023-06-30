package com.kirillmesh.vknewsclient.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kirillmesh.vknewsclient.MainViewModel
import com.kirillmesh.vknewsclient.domain.StatisticType

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {

    Scaffold(
        bottomBar = {
            BottomAppBar {

                val selectedItem = remember {
                    mutableStateOf(0)
                }

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEachIndexed { index, navigationItem ->
                    BottomNavigationItem(
                        selected = selectedItem.value == index,
                        onClick = { selectedItem.value = index },
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
    ) {
        val feedPosts = viewModel.feedPosts.observeAsState(listOf())

        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = feedPosts.value, key = { post -> post.id }) { feedPost ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.removePost(feedPost)
                }

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = {},
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {
                        VkPostCard(
                            feedPost = feedPost,
                            onStatisticViewsClickListener = {
                                viewModel.updateStatistic(
                                    feedPost,
                                    StatisticType.VIEWS
                                )
                            },
                            onStatisticSharesClickListener = {
                                viewModel.updateStatistic(
                                    feedPost,
                                    StatisticType.SHARES
                                )
                            },
                            onStatisticCommentsClickListener = {
                                viewModel.updateStatistic(
                                    feedPost,
                                    StatisticType.COMMENTS
                                )
                            },
                            onStatisticLikesClickListener = {
                                viewModel.updateStatistic(
                                    feedPost,
                                    StatisticType.LIKES
                                )
                            }
                        )
                    }
                )
            }
        }
    }
}