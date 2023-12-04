package com.kirillmesh.vknewsclient.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.kirillmesh.vknewsclient.data.repository.NewsFeedRepository
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.ui.states.CommentsScreenState
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application,
) : ViewModel() {
    private val repository = NewsFeedRepository(application)

    val screenState = repository.getComments(feedPost)
        .map { CommentsScreenState.Comments(feedPost, it) as CommentsScreenState }
}