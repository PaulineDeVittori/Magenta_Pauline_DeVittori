package com.example.magenta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.magenta.model.ColorEntity
import com.example.magenta.viewmodel.ColorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorDetailScreen(
    colorName: String,
    viewModel: ColorViewModel = viewModel(),
    navController: NavHostController
) {
    var color by remember { mutableStateOf<ColorEntity?>(null) }

    // Charger la couleur à partir de Firestore via le ViewModel
    LaunchedEffect(colorName) {
        viewModel.getColorByName(colorName) {
            color = it
        }
    }

    color?.let { colorEntity ->
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Magenta", color = Color(0xFF8B008B)) // Rose foncé
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.toggleFavorite(colorEntity)
                            // Mettre à jour l'état local pour refléter le changement
                            color = colorEntity.copy(isFavorite = !colorEntity.isFavorite)
                        }) {
                            Icon(
                                imageVector = if (colorEntity.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Ajouter aux favoris",
                                tint = if (colorEntity.isFavorite) Color.Red else Color.Gray
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color(android.graphics.Color.parseColor(colorEntity.hex)))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Nom : ${colorEntity.name}", style = MaterialTheme.typography.headlineSmall)
                Text("Code HEX : ${colorEntity.hex}")
                Text("Code RGB : (${colorEntity.red}, ${colorEntity.green}, ${colorEntity.blue})")
            }
        }
    } ?: run {
        // Afficher un indicateur de chargement
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
