package com.kirillmesh.vknewsclient.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kirillmesh.vknewsclient.ui.states.AuthState
import com.kirillmesh.vknewsclient.utils.getToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        try {
            getToken(application)
            _authState.value = AuthState.Authorized
        } catch (e: java.lang.RuntimeException) {
            Log.d("TOKEN_FAIL", e.message.toString())
            _authState.value = AuthState.NotAuthorized
        }

    }

    fun performAuthorization(result: VKAuthenticationResult) {
        if (result is VKAuthenticationResult.Success) {
            _authState.value = AuthState.Authorized
        } else {
            _authState.value = AuthState.NotAuthorized
        }
    }
}