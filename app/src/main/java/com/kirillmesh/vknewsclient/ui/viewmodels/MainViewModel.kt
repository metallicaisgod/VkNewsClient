package com.kirillmesh.vknewsclient.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kirillmesh.vknewsclient.data.repository.NewsFeedRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)
    val authState = repository.authState

    fun performAuthorization() {
        viewModelScope.launch {
            repository.checkAuthState()
        }
    }
}