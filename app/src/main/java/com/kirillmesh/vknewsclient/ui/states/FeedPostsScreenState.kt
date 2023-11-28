package com.kirillmesh.vknewsclient.ui.states

import com.kirillmesh.vknewsclient.domain.FeedPost

sealed class FeedPostsScreenState {

    object Initial : FeedPostsScreenState()

    data class Posts(val posts: List<FeedPost>) : FeedPostsScreenState()

//    data class Comments(val feedPost: FeedPost, val comments: List<Comment>) : HomeScreenState()
}
