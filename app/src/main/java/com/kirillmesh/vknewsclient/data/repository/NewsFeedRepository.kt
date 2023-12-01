package com.kirillmesh.vknewsclient.data.repository

import android.content.Context
import com.kirillmesh.vknewsclient.data.network.NetworkObject
import com.kirillmesh.vknewsclient.data.sharedprefs.TokenManager
import com.kirillmesh.vknewsclient.domain.Comment
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.domain.StatisticElement
import com.kirillmesh.vknewsclient.domain.StatisticType

class NewsFeedRepository(context: Context) {

    private val token = TokenManager(context = context).getToken()

    private val api = NetworkObject.apiService

    private var _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var _comments = mutableListOf<Comment>()
    val comments: List<Comment>
        get() = _comments.toList()

    private var nextFrom: String? = null

    suspend fun loadNewsFeed(): List<FeedPost> {
        val startFrom = nextFrom
        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts
        val response = if (startFrom == null) {
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

        val response = if (!feedPost.isLiked) {
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

    suspend fun removePost(feedPost: FeedPost) {
        api.removePost(
            token,
            feedPost.communityId,
            feedPost.id
        )
        _feedPosts.remove(feedPost)
    }

    suspend fun getComments(feedPost: FeedPost): List<Comment> {
        val commentsTemp = api.getComments(
            token = token,
            ownerId = feedPost.communityId,
            postId = feedPost.id
        ).response.mapToDomain()
        _comments.addAll(commentsTemp)
        return comments
    }
}