package com.kirillmesh.vknewsclient.data.sharedprefs

import android.app.Application
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class TokenManager(val application: Application) {

    fun getToken(): String {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        if(token != null && token.isValid) {
            return token.accessToken
        }
        throw java.lang.RuntimeException("token is null")
    }
}