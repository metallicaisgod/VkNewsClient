package com.kirillmesh.vknewsclient.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.ui.states.FeedPostsScreenState
import com.kirillmesh.vknewsclient.ui.viewmodels.FeedPostsViewModel

@Composable
fun FeedPostsScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    val viewModel: FeedPostsViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(FeedPostsScreenState.Initial)

    when (val currentState = screenState.value) {
        is FeedPostsScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                feedPosts = currentState.posts,
                onCommentsClickListener = onCommentsClickListener
            )
        }
        FeedPostsScreenState.Initial -> {}
    }


}

