package com.kirillmesh.vknewsclient.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.presentation.getApplicationComponent
import com.kirillmesh.vknewsclient.presentation.states.FeedPostsScreenState
import com.kirillmesh.vknewsclient.presentation.theme.DarkBlue
import com.kirillmesh.vknewsclient.presentation.viewmodels.FeedPostsViewModel

@Composable
fun FeedPostsScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    val component = getApplicationComponent()
    val viewModel: FeedPostsViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState(FeedPostsScreenState.Initial)

    FeedPostScreenContent(
        screenState = screenState,
        viewModel = viewModel,
        paddingValues = paddingValues,
        onCommentsClickListener = onCommentsClickListener
    )
}

@Composable
fun FeedPostScreenContent(
    screenState: State<FeedPostsScreenState>,
    viewModel: FeedPostsViewModel,
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
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

