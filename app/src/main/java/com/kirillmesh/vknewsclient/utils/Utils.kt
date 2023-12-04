package com.kirillmesh.vknewsclient.utils

import android.app.Application
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

fun getToken(application: Application): String {
    val storage = VKPreferencesKeyValueStorage(application)
    val token = VKAccessToken.restore(storage)
    if (token != null && token.isValid) {
        return token.accessToken
    }
    throw RuntimeException("Token is null or invalidate!")
}