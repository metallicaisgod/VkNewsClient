package com.kirillmesh.vknewsclient.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kirillmesh.vknewsclient.domain.FeedPost

class CommentsViewModelFactory(
    private val feedPost: FeedPost,
    private val context: Context,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CommentsViewModel(feedPost, context) as T
    }
}