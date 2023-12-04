package com.kirillmesh.vknewsclient.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kirillmesh.vknewsclient.data.repository.NewsFeedRepository
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.extensions.mergeWith
import com.kirillmesh.vknewsclient.ui.states.FeedPostsScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FeedPostsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)
    private val newsFeedFlow = repository.newsFeed

    private val loadNextDataFlow = MutableSharedFlow<FeedPostsScreenState>()


    val screenState = repository.newsFeed
        .filter { it.isNotEmpty() }
        .map { FeedPostsScreenState.Posts(it) as FeedPostsScreenState }
        .onStart { emit(FeedPostsScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextNewsFeed() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                FeedPostsScreenState.Posts(
                    posts = newsFeedFlow.value,
                    isNextNewsFeedLoading = true
                )
            )
            repository.needNextData()
        }
    }

    fun changeLikesCount(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikesInPost(feedPost)
        }
    }

    fun removePost(post: FeedPost) {
        viewModelScope.launch {
            repository.removePost(post)
        }
    }
}