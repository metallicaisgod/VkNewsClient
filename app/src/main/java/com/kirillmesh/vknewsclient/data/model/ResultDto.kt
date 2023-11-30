package com.kirillmesh.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("response") val response: ResponseDto
)
