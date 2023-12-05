package com.kirillmesh.vknewsclient.domain.usecases

import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class NeedNextDataUseCase @Inject constructor(
    private val repository: NewsFeedRepository,
) {
    suspend operator fun invoke() {
        repository.needNextData()
    }
}