package com.kirillmesh.vknewsclient.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kirillmesh.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.kirillmesh.vknewsclient.domain.usecases.CheckAuthStateUseCase
import com.kirillmesh.vknewsclient.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateUseCase = GetAuthStateUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateUseCase()

    fun performAuthorization() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}