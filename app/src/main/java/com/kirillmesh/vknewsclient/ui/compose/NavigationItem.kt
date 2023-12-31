package com.kirillmesh.vknewsclient.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.kirillmesh.vknewsclient.R
import com.kirillmesh.vknewsclient.ui.navigation.Screen

sealed class NavigationItem (
    val screen: Screen,
    val textRexId: Int,
    val icon: ImageVector
){

    object Home : NavigationItem(
        screen = Screen.NewsFeed,
        textRexId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    object Favorite : NavigationItem(
        screen = Screen.Favourite,
        textRexId = R.string.navigation_item_favorite,
        icon = Icons.Outlined.Favorite
    )

    object Profile : NavigationItem(
        screen = Screen.Profile,
        textRexId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}
