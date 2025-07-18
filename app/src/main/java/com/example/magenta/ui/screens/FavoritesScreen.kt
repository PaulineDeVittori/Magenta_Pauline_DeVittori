package com.example.magenta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.magenta.ui.components.ColorCard
import com.example.magenta.viewmodel.ColorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: ColorViewModel = viewModel()
) {
    val allColors = viewModel.colors.collectAsState().value
    val favoriteNames = viewModel.favorites.collectAsState().value

    // Récupère les couleurs dont le nom est dans la liste des noms favoris
    val favoriteColors = allColors.filter { favoriteNames.contains(it.name) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Favoris") }
            )
        },
        content = { paddingValues ->
            if (favoriteColors.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("Aucune couleur en favoris pour l’instant.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                ) {
                    items(favoriteColors) { color ->
                        ColorCard(
                            color = color,
                            isFavorite = true,
                            onClick = { navController.navigate("detail/${color.name}") },
                            onToggleFavorite = { viewModel.toggleFavorite(color) }
                        )
                    }
                }
            }
        }
    )
}

