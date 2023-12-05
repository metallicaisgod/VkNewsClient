package com.kirillmesh.vknewsclient.domain.usecases

import com.kirillmesh.vknewsclient.domain.entities.AuthState
import com.kirillmesh.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: NewsFeedRepository,
) {
    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthState()
    }
}