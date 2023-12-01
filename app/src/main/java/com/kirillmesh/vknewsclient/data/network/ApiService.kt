package com.kirillmesh.vknewsclient.data.network

import com.kirillmesh.vknewsclient.data.model.ResponseLikesDto
import com.kirillmesh.vknewsclient.data.model.ResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended")
    suspend fun loadNewsFeed(@Query("access_token") token: String): ResultDto

    @GET("newsfeed.get")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String,
    ): ResultDto

    @GET("likes.add?type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long,
    ): ResponseLikesDto

    @GET("likes.delete?type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long,
    ): ResponseLikesDto

    @GET("newsfeed.ignoreItem?type=wall")
    suspend fun removePost(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long,
    )

}