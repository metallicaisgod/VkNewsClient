package com.kirillmesh.vknewsclient.ui.states

import com.kirillmesh.vknewsclient.domain.Comment
import com.kirillmesh.vknewsclient.domain.FeedPost

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(val feedPost: FeedPost, val comments: List<Comment>) : CommentsScreenState()
}
