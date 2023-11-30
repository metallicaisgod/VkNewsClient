package com.kirillmesh.vknewsclient.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object NetworkObject {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("v", "5.199")
                .build()
            request.url(url)
            chain.proceed(request.build())
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.vk.ru/method/")
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create()
}