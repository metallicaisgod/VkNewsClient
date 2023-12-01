package com.kirillmesh.vknewsclient.data.model

import com.google.gson.annotations.SerializedName
import com.kirillmesh.vknewsclient.domain.Comment

data class CommentsResponseDto(
    @SerializedName("items") val comments: List<CommentDto>,
    @SerializedName("profiles") val profiles: List<UserProfileDto>,
) {

    fun mapToDomain(): List<Comment> {
        val result = mutableListOf<Comment>()

        for (comment in comments) {
            if (comment.text.isBlank()) continue
            val profile = profiles.find { it.id == comment.fromId } ?: continue

            val newComment = Comment(
                id = comment.id,
                authorName = profile.first_name + " " + profile.last_name,
                authorAvatarUrl = profile.photo,
                commentText = comment.text,
                commentTime = comment.date.timestampToDate()
            )
            result.add(newComment)
        }
        return result
    }
}
