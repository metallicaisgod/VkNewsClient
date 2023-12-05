package com.kirillmesh.vknewsclient.domain.usecases

import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class RemovePostUseCase @Inject constructor(
    private val repository: NewsFeedRepository,
) {
    suspend operator fun invoke(feedPost: FeedPost) {
        repository.removePost(feedPost)
    }
}