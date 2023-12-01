package com.kirillmesh.vknewsclient.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticElement(
    val type: StatisticType,
    val count: Long = 0,
) : Parcelable

enum class StatisticType {
    VIEWS, COMMENTS, SHARES, LIKES
}
