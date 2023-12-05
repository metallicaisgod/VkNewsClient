package com.kirillmesh.vknewsclient.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.kirillmesh.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.domain.usecases.GetCommentsUseCase
import com.kirillmesh.vknewsclient.presentation.states.CommentsScreenState
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application,
) : ViewModel() {
    private val repository = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository = repository)

    val screenState = getCommentsUseCase(feedPost)
        .map { CommentsScreenState.Comments(feedPost, it) as CommentsScreenState }
}