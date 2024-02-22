package com.truta.proteus_android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : NavItem("home", Icons.Rounded.Home, "Home")

    companion object {
        val items: List<NavItem> = listOf(Home)
    }
}