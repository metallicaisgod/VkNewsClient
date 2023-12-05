package com.kirillmesh.vknewsclient.presentation.states

import com.kirillmesh.vknewsclient.domain.entities.Comment
import com.kirillmesh.vknewsclient.domain.entities.FeedPost

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(val feedPost: FeedPost, val comments: List<Comment>) : CommentsScreenState()
}
