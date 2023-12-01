package com.kirillmesh.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("id") val id: Long,
    @SerializedName("photo_100") val photo: String?,
    @SerializedName("first_name") val first_name: String,
    @SerializedName("last_name") val last_name: String,
)
