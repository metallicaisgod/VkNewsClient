package com.kirillmesh.vknewsclient.domain.usecases

import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetNewsFeedUseCase @Inject constructor(
    private val repository: NewsFeedRepository,
) {
    operator fun invoke(): StateFlow<List<FeedPost>> {
        return repository.getNewsFeed()
    }
}