package com.example.magenta.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable fun HomeScreen() = Text("Bienvenue sur la page d'accueil")
//@Composable fun DictionaryScreen(navController: NavHostController) = Text("Page dictionnaire")
@Composable fun SearchScreen() = Text("Page recherche")
@Composable fun RandomColorScreen() = Text("Couleur aléatoire")
@Composable fun FavoritesScreen() = Text("Favoris")
