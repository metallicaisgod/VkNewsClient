package com.kirillmesh.vknewsclient.data.sharedprefs

import android.content.Context
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class TokenManager(private val context: Context) {

    fun getToken(): String {
        val storage = VKPreferencesKeyValueStorage(context)
        val token = VKAccessToken.restore(storage)
        if (token != null && token.isValid) {
            return token.accessToken
        }
        throw java.lang.RuntimeException("token is null")
    }
}