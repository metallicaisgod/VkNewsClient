package com.kirillmesh.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("count") val count: Long,
    @SerializedName("user_likes") val userLikes: Int,
)
