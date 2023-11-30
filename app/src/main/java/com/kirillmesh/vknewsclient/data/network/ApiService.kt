package com.kirillmesh.vknewsclient.data.network

import com.kirillmesh.vknewsclient.data.model.ResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get")
    suspend fun loadNewsFeed(@Query("access_token") token: String): ResultDto
}