package com.kirillmesh.vknewsclient.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kirillmesh.vknewsclient.data.sharedprefs.TokenManager
import com.kirillmesh.vknewsclient.ui.states.AuthState
import com.vk.api.sdk.auth.VKAuthenticationResult

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        val token = TokenManager(application).getToken()
        Log.d("Token", token ?: "null")
        _authState.value = if (token != null) AuthState.Authorized else AuthState.NotAuthorized
    }

    fun performAuthorization(result: VKAuthenticationResult) {
        if (result is VKAuthenticationResult.Success) {
            _authState.value = AuthState.Authorized
        } else {
            _authState.value = AuthState.NotAuthorized
        }
    }
}