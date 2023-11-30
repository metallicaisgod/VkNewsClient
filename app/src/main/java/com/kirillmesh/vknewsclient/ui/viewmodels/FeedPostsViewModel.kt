package com.kirillmesh.vknewsclient.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.kirillmesh.vknewsclient.data.network.NetworkObject
import com.kirillmesh.vknewsclient.data.sharedprefs.TokenManager
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.domain.StatisticType
import com.kirillmesh.vknewsclient.ui.states.FeedPostsScreenState
import kotlinx.coroutines.launch

class FeedPostsViewModel(application: Application) : AndroidViewModel(application) {

    private val startState = FeedPostsScreenState.Initial

    private val _screenState = MutableLiveData<FeedPostsScreenState>(startState)
    val screenState: LiveData<FeedPostsScreenState> = _screenState

    init {
        loadNewsFeed()
    }

    private fun loadNewsFeed() {
        viewModelScope.launch {
            val token = TokenManager(application = getApplication()).getToken()
            if(token != null) {
                val posts = NetworkObject.apiService.loadNewsFeed(token).response.mapToDomain()
                _screenState.value = FeedPostsScreenState.Posts(posts)
            }
        }
    }

    fun updateStatistic(post: FeedPost, type: StatisticType) {

        val currentState = _screenState.value
        if(currentState !is FeedPostsScreenState.Posts) return
        val modifiedList = currentState.posts.toMutableList()
        modifiedList.replaceAll {
            if (it == post) {
                val newStatistics = it.statistics.toMutableList().apply {
                    replaceAll { statisticElement ->
                        if (statisticElement.type == type) {
                            statisticElement.copy(count = statisticElement.count + 1)
                        } else {
                            statisticElement
                        }
                    }
                }
                it.copy(statistics = newStatistics)
            } else {
                it
            }
        }
        _screenState.value = FeedPostsScreenState.Posts(modifiedList)
    }

    fun removePost(post: FeedPost) {
        val currentState = _screenState.value
        if(currentState !is FeedPostsScreenState.Posts) return
        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(post)
        _screenState.value = FeedPostsScreenState.Posts(modifiedList)
    }
}