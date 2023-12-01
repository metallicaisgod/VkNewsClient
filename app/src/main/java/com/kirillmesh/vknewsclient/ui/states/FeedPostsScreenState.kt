package com.kirillmesh.vknewsclient.ui.states

import com.kirillmesh.vknewsclient.domain.FeedPost

sealed class FeedPostsScreenState {

    object Initial : FeedPostsScreenState()

    object Loading : FeedPostsScreenState()

    data class Posts(val posts: List<FeedPost>, val isNextNewsFeedLoading: Boolean = false) :
        FeedPostsScreenState()

//    data class Comments(val feedPost: FeedPost, val comments: List<Comment>) : HomeScreenState()
}
