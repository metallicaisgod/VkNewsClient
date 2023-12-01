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

    private var nextFrom: String?= null

    suspend fun loadNewsFeed(): List<FeedPost> {
        val startFrom = nextFrom
        if(startFrom == null && feedPosts.isNotEmpty()) return feedPosts
        val response = if(startFrom == null) {
            api.loadNewsFeed(token).response
        } else {
            api.loadNewsFeed(token, startFrom).response
        }
        nextFrom = response.nextFrom
        val posts = response.mapToDomain()
        _feedPosts.addAll(posts)
        return feedPosts
    }

    suspend fun changeLikesInPost(feedPost: FeedPost) {

        val response = if(!feedPost.isLiked) {
            api.addLike(
                token,
                feedPost.communityId,
                feedPost.id
            )
        } else {
            api.deleteLike(
                token,
                feedPost.communityId,
                feedPost.id
            )
        }

        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticElement(type = StatisticType.LIKES, count = newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }
}