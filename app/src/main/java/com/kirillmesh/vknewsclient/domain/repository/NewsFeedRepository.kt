package com.kirillmesh.vknewsclient.domain.repository

import com.kirillmesh.vknewsclient.domain.entities.AuthState
import com.kirillmesh.vknewsclient.domain.entities.Comment
import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getNewsFeed(): StateFlow<List<FeedPost>>

    fun getAuthState(): StateFlow<AuthState>

    fun getComments(feedPost: FeedPost): StateFlow<List<Comment>>

    suspend fun checkAuthState()

    suspend fun needNextData()

    suspend fun changeLikesInPost(feedPost: FeedPost)

    suspend fun removePost(feedPost: FeedPost)
}