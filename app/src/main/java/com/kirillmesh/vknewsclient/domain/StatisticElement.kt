package com.kirillmesh.vknewsclient.domain

data class StatisticElement (
    val type: StatisticType,
    val count: Int = 0
)

enum class StatisticType{
    VIEWS, COMMENTS, SHARES, LIKES
}
