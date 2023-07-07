package com.kirillmesh.vknewsclient.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kirillmesh.vknewsclient.MainViewModel
import com.kirillmesh.vknewsclient.domain.StatisticType

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val feedPosts = viewModel.feedPosts.observeAsState(listOf())


//    if(feedPosts.value.isNotEmpty()) {
//        val comments = mutableListOf<Comment>().apply {
//            repeat(20){
//                add(Comment(id = it))
//            }
//        }
//        CommentsScreen(feedPost = feedPosts.value.get(0), comments = comments)
//    }

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
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