package com.kirillmesh.vknewsclient.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirillmesh.vknewsclient.data.repository.NewsFeedRepository
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.ui.states.CommentsScreenState
import kotlinx.coroutines.launch

class CommentsViewModel(
    feedPost: FeedPost,
    context: Context,
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsFeedRepository(context)

    init {
        loadComments(feedPost)
    }

    fun loadComments(feedPost: FeedPost) {
        viewModelScope.launch {
            val commentsList = repository.getComments(feedPost)
            _screenState.value = CommentsScreenState.Comments(feedPost, commentsList)
        }
    }
}