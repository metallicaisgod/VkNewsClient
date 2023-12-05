package com.kirillmesh.vknewsclient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.domain.usecases.GetCommentsUseCase
import com.kirillmesh.vknewsclient.presentation.states.CommentsScreenState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    getCommentsUseCase: GetCommentsUseCase,
) : ViewModel() {

    val screenState = getCommentsUseCase(feedPost)
        .map { CommentsScreenState.Comments(feedPost, it) as CommentsScreenState }
}