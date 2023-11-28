package com.kirillmesh.vknewsclient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kirillmesh.vknewsclient.domain.Comment
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.ui.states.CommentsScreenState

class CommentsViewModel(
    feedPost: FeedPost
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    fun loadComments(feedPost: FeedPost) {
        val commentsList = mutableListOf<Comment>().apply {
            repeat(10) {
                add(Comment(id = it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost, commentsList)
    }
}