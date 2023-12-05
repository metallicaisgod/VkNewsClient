package com.kirillmesh.vknewsclient.domain.entities

data class Comment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String?,
    val commentText: String,
    val commentTime: String,
)
