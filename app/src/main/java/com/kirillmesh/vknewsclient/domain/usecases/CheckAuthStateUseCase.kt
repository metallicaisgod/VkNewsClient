package com.kirillmesh.vknewsclient.domain.usecases

import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase(
    private val repository: NewsFeedRepository,
) {
    suspend operator fun invoke() {
        repository.checkAuthState()
    }
}