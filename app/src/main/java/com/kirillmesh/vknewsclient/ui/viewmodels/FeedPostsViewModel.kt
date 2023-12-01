package com.kirillmesh.vknewsclient.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kirillmesh.vknewsclient.data.repository.NewsFeedRepository
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.ui.states.FeedPostsScreenState
import kotlinx.coroutines.launch

class FeedPostsViewModel(application: Application) : AndroidViewModel(application) {

    private val startState = FeedPostsScreenState.Initial

    private val _screenState = MutableLiveData<FeedPostsScreenState>(startState)
    val screenState: LiveData<FeedPostsScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        _screenState.value = FeedPostsScreenState.Loading
        loadNewsFeed()
    }

    private fun loadNewsFeed() {
        viewModelScope.launch {
            val posts = repository.loadNewsFeed()
            _screenState.value = FeedPostsScreenState.Posts(posts)
        }
    }

    fun loadNextNewsFeed() {
        _screenState.value = FeedPostsScreenState.Posts(
            posts = repository.feedPosts,
            isNextNewsFeedLoading = true
        )
        loadNewsFeed()
    }

    fun changeLikesCount(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikesInPost(feedPost)
            _screenState.value = FeedPostsScreenState.Posts(repository.feedPosts)
        }
    }

    fun removePost(post: FeedPost) {
        val currentState = _screenState.value
        if (currentState !is FeedPostsScreenState.Posts) return
        viewModelScope.launch {
            repository.removePost(post)
            _screenState.value = FeedPostsScreenState.Posts(repository.feedPosts)
        }
    }
}