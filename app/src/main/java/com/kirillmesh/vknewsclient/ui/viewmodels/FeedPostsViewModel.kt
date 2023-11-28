package com.kirillmesh.vknewsclient.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.domain.StatisticType
import com.kirillmesh.vknewsclient.ui.states.FeedPostsScreenState

class FeedPostsViewModel : ViewModel() {

    private val postList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it, communityName = "Group Name $it"))
        }
    }
    private val startState = FeedPostsScreenState.Posts(postList)

    private val _screenState = MutableLiveData<FeedPostsScreenState>(startState)
    val screenState: LiveData<FeedPostsScreenState> = _screenState

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