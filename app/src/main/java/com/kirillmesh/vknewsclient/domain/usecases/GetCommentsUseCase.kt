package com.kirillmesh.vknewsclient.domain.usecases

import com.kirillmesh.vknewsclient.domain.entities.Comment
import com.kirillmesh.vknewsclient.domain.entities.FeedPost
import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetCommentsUseCase(
    private val repository: NewsFeedRepository,
) {
    operator fun invoke(feedPost: FeedPost): StateFlow<List<Comment>> {
        return repository.getComments(feedPost)
    }
}