package com.kirillmesh.vknewsclient.domain

data class Comment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String?,
    val commentText: String,
    val commentTime: String,
)
