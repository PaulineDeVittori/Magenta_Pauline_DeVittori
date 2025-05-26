package com.example.magenta.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.magenta.data.colorList
import com.example.magenta.ui.screens.ColorDetailScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Dictionary.route) { DictionaryScreen(navController) }
        composable(BottomNavItem.Search.route) { SearchScreen() }
        composable(BottomNavItem.Random.route) { RandomColorScreen() }
        composable(BottomNavItem.Favorites.route) { FavoritesScreen() }

        composable("detail/{colorName}") { backStackEntry ->
            val colorName = backStackEntry.arguments?.getString("colorName") ?: return@composable
            val color = colorList.find { it.name == colorName }
            if (color != null) {
                ColorDetailScreen(color)
            } else {
                Text("Couleur introuvable")
            }
        }
    }
}

