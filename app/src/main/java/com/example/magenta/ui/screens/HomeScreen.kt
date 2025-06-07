package com.example.magenta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.magenta.model.ColorEntity
import com.example.magenta.ui.components.ColorCard
import com.example.magenta.viewmodel.ColorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: ColorViewModel = viewModel()
) {
    val colorList by viewModel.colors.collectAsState()
    val latestColors = colorList.takeLast(5).reversed()
    val favorites = viewModel.favorites.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Magenta", color = Color(0xFF880E4F))
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Bienvenue sur Magenta, une application dédiée à la couleur.\n" +
                            "Vous y trouverez de nombreuses fiches couleurs à explorer.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    "Nouvelles couleurs ajoutées :",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF880E4F),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(latestColors) { color ->
                ColorCard(
                    color = color,
                    isFavorite = favorites.contains(color.name), // <- correction ici
                    onClick = { navController.navigate("detail/${color.name}") },
                    onToggleFavorite = { viewModel.toggleFavorite(color) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
