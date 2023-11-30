package com.kirillmesh.vknewsclient.data.model

import com.google.gson.annotations.SerializedName
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.domain.StatisticElement
import com.kirillmesh.vknewsclient.domain.StatisticType
import kotlin.math.absoluteValue

data class ResponseDto(
    @SerializedName("items") val posts: List<FeedPostDto>,
    @SerializedName("groups") val groups: List<GroupDto>,
) {
    fun mapToDomain(): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue

            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                publicationData = post.date,
                communityAvatarUrl = group.photoUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticElement(StatisticType.COMMENTS, post.comments.count),
                    StatisticElement(StatisticType.LIKES, post.likes.count),
                    StatisticElement(StatisticType.VIEWS, post.views.count),
                    StatisticElement(StatisticType.SHARES, post.reposts.count),
                )
            )

            result.add(feedPost)
        }
        return result
    }
}


