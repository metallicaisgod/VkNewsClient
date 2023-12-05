package com.kirillmesh.vknewsclient.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kirillmesh.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.domain.usecases.ChangeLikesUseCase
import com.kirillmesh.vknewsclient.domain.usecases.GetNewsFeedUseCase
import com.kirillmesh.vknewsclient.domain.usecases.NeedNextDataUseCase
import com.kirillmesh.vknewsclient.domain.usecases.RemovePostUseCase
import com.kirillmesh.vknewsclient.extensions.mergeWith
import com.kirillmesh.vknewsclient.presentation.states.FeedPostsScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FeedPostsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getNewsFeedUseCase =  GetNewsFeedUseCase(repository)
    private val needNextDataUseCase = NeedNextDataUseCase(repository)
    private val changeLikesUseCase = ChangeLikesUseCase(repository)
    private val removePostUseCase = RemovePostUseCase(repository)

    private val newsFeedFlow = getNewsFeedUseCase()
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("FeedPostsViewModel", "Exception catched by exceptionHandler")
    }

    private val loadNextDataFlow = MutableSharedFlow<FeedPostsScreenState>()


    val screenState = newsFeedFlow
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
            needNextDataUseCase()
        }
    }

    fun changeLikesCount(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikesUseCase(feedPost)
        }
    }

    fun removePost(post: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            removePostUseCase(post)
        }
    }
}