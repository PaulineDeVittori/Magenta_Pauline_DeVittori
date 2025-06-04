package com.example.magenta.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Accueil", Icons.Filled.Home)
    object Dictionary : BottomNavItem("dictionary", "Dico", Icons.Filled.Menu)
    object Search : BottomNavItem("search", "Recherche", Icons.Filled.Search)
    object Random : BottomNavItem("random", "Aléatoire", Icons.Filled.Refresh)
    object Favorites : BottomNavItem("favorites", "Favoris", Icons.Filled.Favorite)
}
