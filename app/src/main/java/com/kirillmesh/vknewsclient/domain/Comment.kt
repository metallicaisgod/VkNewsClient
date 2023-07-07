package com.kirillmesh.vknewsclient.domain

import com.kirillmesh.vknewsclient.R

data class Comment(
    val id: Int,
    val authorName: String = "Author Name",
    val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Long comment text",
    val commentTime: String = "14:00"
)
