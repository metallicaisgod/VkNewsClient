package com.kirillmesh.vknewsclient.domain

import com.kirillmesh.vknewsclient.R

data class FeedPost(
    val id: Int,
    val communityName: String = "GroupName",
    val publicationData: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vel augue ex. In mattis turpis neque, sit amet tempus ligula viverra non. Nam egestas nibh id nibh imperdiet tincidunt.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticElement> = listOf(
        StatisticElement(StatisticType.VIEWS, 966),
        StatisticElement(StatisticType.SHARES, 203),
        StatisticElement(StatisticType.COMMENTS, 11),
        StatisticElement(StatisticType.LIKES, 463),
    )
)
