package com.kirillmesh.vknewsclient.ui.states

sealed class AuthState {

    object Initial : AuthState()

    object Authorized : AuthState()

    object NotAuthorized : AuthState()
}
