package com.example.magenta.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.magenta.data.colorList
import com.example.magenta.ui.screens.ColorDetailScreen
import com.example.magenta.ui.screens.DictionaryScreen
import com.example.magenta.ui.screens.HomeScreen
import com.example.magenta.ui.screens.SearchScreen
import com.example.magenta.ui.screens.FavoritesScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen(navController) }
        composable(BottomNavItem.Dictionary.route) { DictionaryScreen(navController) }
        composable(BottomNavItem.Search.route) { SearchScreen(navController) }

        composable(BottomNavItem.Random.route) { RandomColorScreen() }
        composable(BottomNavItem.Favorites.route) { FavoritesScreen(navController) }


        composable("detail/{colorName}") { backStackEntry ->
            val colorName = backStackEntry.arguments?.getString("colorName") ?: ""
            ColorDetailScreen(colorName = colorName, navController = navController)
        }
    }
}

