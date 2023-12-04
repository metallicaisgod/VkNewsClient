package com.kirillmesh.vknewsclient.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.ui.states.FeedPostsScreenState
import com.kirillmesh.vknewsclient.ui.theme.DarkBlue
import com.kirillmesh.vknewsclient.ui.viewmodels.FeedPostsViewModel

@Composable
fun FeedPostsScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    val viewModel: FeedPostsViewModel = viewModel()
    val screenState = viewModel.screenState.collectAsState(FeedPostsScreenState.Initial)

    when (val currentState = screenState.value) {
        is FeedPostsScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                feedPosts = currentState.posts,
                onCommentsClickListener = onCommentsClickListener,
                isNextNewsFeedLoading = currentState.isNextNewsFeedLoading
            )
        }
        FeedPostsScreenState.Initial -> {}
        FeedPostsScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}

