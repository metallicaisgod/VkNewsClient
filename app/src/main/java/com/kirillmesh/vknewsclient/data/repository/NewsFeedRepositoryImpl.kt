package com.kirillmesh.vknewsclient.data.repository

import com.kirillmesh.vknewsclient.data.network.ApiService
import com.kirillmesh.vknewsclient.domain.entities.*
import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import com.kirillmesh.vknewsclient.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val storage: VKPreferencesKeyValueStorage,
) : NewsFeedRepository {

    private val vkToken
        get() = VKAccessToken.restore(storage)

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
                api.loadNewsFeed(getAccessToken()).response
            } else {
                api.loadNewsFeed(getAccessToken(), startFrom).response
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
            val currentToken = vkToken
            val loggedIn = currentToken != null && currentToken.isValid
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override suspend fun checkAuthState() {
        checkAuthStateEvent.emit(Unit)
    }

    override suspend fun needNextData() {
        nextDataNeeded.emit(Unit)
    }


    override suspend fun changeLikesInPost(feedPost: FeedPost) {

        val response = if (!feedPost.isLiked) {
            api.addLike(
                getAccessToken(),
                feedPost.communityId,
                feedPost.id
            )
        } else {
            api.deleteLike(
                getAccessToken(),
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
            getAccessToken(),
            feedPost.communityId,
            feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshDataFlow.emit(feedPosts)
    }

    override fun getNewsFeed(): StateFlow<List<FeedPost>> = newsFeedFlow

    override fun getAuthState(): StateFlow<AuthState> = authStateFlow

    override fun getComments(feedPost: FeedPost): StateFlow<List<Comment>> = flow {
        val commentsTemp = api.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        ).response.mapToDomain()
        emit(commentsTemp)
    }.retry {
        delay(RETRY_TIMEOUT_IN_MILLIS)
        true
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        listOf()
    )

    private fun getAccessToken(): String {
        return vkToken?.accessToken ?: throw IllegalStateException("Token is null")
    }

    companion object {
        const val RETRY_TIMEOUT_IN_MILLIS = 3000L
    }
}