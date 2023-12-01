package com.kirillmesh.vknewsclient.data.repository

import android.app.Application
import com.kirillmesh.vknewsclient.data.network.NetworkObject
import com.kirillmesh.vknewsclient.data.sharedprefs.TokenManager
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.domain.StatisticElement
import com.kirillmesh.vknewsclient.domain.StatisticType

class NewsFeedRepository(application: Application) {

    private val token = TokenManager(application = application).getToken()

    private val api = NetworkObject.apiService

    private var _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    suspend fun loadNewsFeed(): List<FeedPost> {
        val posts = api.loadNewsFeed(token).response.mapToDomain()
        _feedPosts.addAll(posts)
        return posts
    }

    suspend fun addLike(feedPost: FeedPost) {
        val response = api.addLike(
            token,
            feedPost.communityId,
            feedPost.id
        )

        val newLikesCount = response.likes.count
        changeLikesInPost(newLikesCount, feedPost, true)
    }

    suspend fun deleteLike(feedPost: FeedPost) {
        val response = api.deleteLike(
            token,
            feedPost.communityId,
            feedPost.id
        )

        val newLikesCount = response.likes.count
        changeLikesInPost(newLikesCount, feedPost, false)
    }

    private fun changeLikesInPost(newLikesCount: Long, feedPost: FeedPost, isLiked: Boolean){
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticElement(type = StatisticType.LIKES, count = newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }

}