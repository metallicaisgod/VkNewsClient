package com.kirillmesh.vknewsclient.domain.usecases

import com.kirillmesh.vknewsclient.domain.entities.AuthState
import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase(
    private val repository: NewsFeedRepository,
) {
    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthState()
    }
}