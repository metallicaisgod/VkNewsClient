package com.kirillmesh.vknewsclient.domain.usecases

import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class ChangeLikesUseCase @Inject constructor(
    private val repository: NewsFeedRepository,
) {
    suspend operator fun invoke(feedPost: FeedPost) {
        repository.changeLikesInPost(feedPost)
    }
}