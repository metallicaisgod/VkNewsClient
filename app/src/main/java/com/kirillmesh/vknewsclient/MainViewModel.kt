package com.kirillmesh.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.domain.StatisticType

class MainViewModel : ViewModel() {

    private val postList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(FeedPost(id = it, communityName = "Group Name $it"))
        }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(postList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    fun updateStatistic(post: FeedPost, type: StatisticType) {
        val modifiedList = _feedPosts.value?.toMutableList() ?: throw IllegalStateException()
        modifiedList.replaceAll{
            if(it == post) {
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
        _feedPosts.value = modifiedList
    }

    fun removePost(post: FeedPost) {
        val modifiedList = _feedPosts.value?.toMutableList() ?: throw IllegalStateException()
        modifiedList.remove(post)
        _feedPosts.value = modifiedList
    }
}