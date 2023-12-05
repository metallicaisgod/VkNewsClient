package com.kirillmesh.vknewsclient.data.repository

import android.app.Application
import android.util.Log
import com.kirillmesh.vknewsclient.data.network.NetworkObject
import com.kirillmesh.vknewsclient.domain.entities.Comment
import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.domain.entities.StatisticElement
import com.kirillmesh.vknewsclient.domain.entities.StatisticType
import com.kirillmesh.vknewsclient.extensions.mergeWith
import com.kirillmesh.vknewsclient.domain.entities.AuthState
import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import com.kirillmesh.vknewsclient.utils.getToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class NewsFeedRepositoryImpl(private val application: Application) : NewsFeedRepository {

    private val token
    get() = getToken(application)


    private val api = NetworkObject.apiService

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeeded = MutableSharedFlow<Unit>(replay = 1)
    private val refreshDataFlow = MutableSharedFlow<List<FeedPost>>()
    private val loadedDataFlow = flow {
        nextDataNeeded.emit(Unit)
        nextDataNeeded.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
            }
            val response = if (startFrom == null) {
                api.loadNewsFeed(token).response
            } else {
                api.loadNewsFeed(token, startFrom).response
            }
            nextFrom = response.nextFrom
            val posts = response.mapToDomain()
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT_IN_MILLIS)
        true
    }

    private var _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var _comments = mutableListOf<Comment>()
    val comments: List<Comment>
        get() = _comments.toList()

    private var nextFrom: String? = null

    private val newsFeedFlow: StateFlow<List<FeedPost>> =
        loadedDataFlow
            .mergeWith(refreshDataFlow)
            .stateIn(
                coroutineScope,
                started = SharingStarted.Lazily,
                initialValue = feedPosts
            )

    private val checkAuthStateEvent = MutableSharedFlow<Unit>(replay = 1)
    private val authStateFlow = flow {
        checkAuthStateEvent.emit(Unit)
        checkAuthStateEvent.collect {
            try {
                getToken(application)
                emit(AuthState.Authorized)
            } catch (e: java.lang.RuntimeException) {
                Log.d("TOKEN_FAIL", e.message.toString())
                emit(AuthState.NotAuthorized)
            }
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override suspend fun checkAuthState(){
        checkAuthStateEvent.emit(Unit)
    }

    override suspend fun needNextData() {
        nextDataNeeded.emit(Unit)
    }



    override suspend fun changeLikesInPost(feedPost: FeedPost) {

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
        refreshDataFlow.emit(feedPosts)
    }

    override suspend fun removePost(feedPost: FeedPost) {
        api.removePost(
            token,
            feedPost.communityId,
            feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshDataFlow.emit(feedPosts)
    }

    override fun getNewsFeed(): StateFlow<List<FeedPost>> = newsFeedFlow

    override fun getAuthState(): StateFlow<AuthState>  = authStateFlow

    override fun getComments(feedPost: FeedPost): StateFlow<List<Comment>> = flow {
        val commentsTemp = api.getComments(
            token = token,
            ownerId = feedPost.communityId,
            postId = feedPost.id
        ).response.mapToDomain()
        _comments.addAll(commentsTemp)
        emit(comments)
    }.retry {
        delay(RETRY_TIMEOUT_IN_MILLIS)
        true
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        listOf()
    )

    companion object {
        const val RETRY_TIMEOUT_IN_MILLIS = 3000L
    }
}