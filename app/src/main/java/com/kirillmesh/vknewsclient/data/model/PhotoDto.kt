package com.kirillmesh.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("size") val photoUrls: List<PhotoUrlDto>?
)
