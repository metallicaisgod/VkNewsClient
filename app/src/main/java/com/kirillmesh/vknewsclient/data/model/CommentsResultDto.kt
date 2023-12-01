package com.kirillmesh.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class CommentsResultDto(
    @SerializedName("response") val response: CommentsResponseDto,
)
