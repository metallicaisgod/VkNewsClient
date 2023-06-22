package com.kirillmesh.vknewsclient.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.kirillmesh.vknewsclient.R

sealed class NavigationItem (
    val textRexId: Int,
    val icon: ImageVector
){

    object Home : NavigationItem(
        textRexId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    object Favorite : NavigationItem(
        textRexId = R.string.navigation_item_favorite,
        icon = Icons.Outlined.Favorite
    )

    object Profile : NavigationItem(
        textRexId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}
