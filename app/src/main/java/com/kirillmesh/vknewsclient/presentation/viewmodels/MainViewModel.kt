package com.kirillmesh.vknewsclient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirillmesh.vknewsclient.domain.usecases.CheckAuthStateUseCase
import com.kirillmesh.vknewsclient.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
) : ViewModel() {

    val authState = getAuthStateUseCase()

    fun performAuthorization() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}