package com.kirillmesh.vknewsclient.data.model

import com.google.gson.annotations.SerializedName
import com.kirillmesh.vknewsclient.domain.FeedPost
import com.kirillmesh.vknewsclient.domain.StatisticElement
import com.kirillmesh.vknewsclient.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

data class ResponseDto(
    @SerializedName("items") val posts: List<FeedPostDto>,
    @SerializedName("groups") val groups: List<GroupDto>,
) {
    fun mapToDomain(): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        for (post in posts) {
            if(post.id == 0L) continue
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue


            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationData = post.date.timestampToDate(),
                communityAvatarUrl = group.photoUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticElement(StatisticType.COMMENTS, post.comments?.count ?: 0),
                    StatisticElement(StatisticType.LIKES, post.likes?.count ?: 0),
                    StatisticElement(StatisticType.VIEWS, post.views?.count ?: 0),
                    StatisticElement(StatisticType.SHARES, post.reposts?.count ?: 0),
                ),
                isLiked = ((post.likes?.userLikes ?: 0) > 0)
            )

            result.add(feedPost)
        }
        return result
    }

    private fun Long.timestampToDate(): String {
        val date = Date(this * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}


