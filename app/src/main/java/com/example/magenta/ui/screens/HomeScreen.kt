package com.example.magenta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.magenta.model.ColorEntity
import com.example.magenta.viewmodel.ColorViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.magenta.ui.components.ColorCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: ColorViewModel = viewModel()
) {
    val colorList by viewModel.colors.collectAsState()

    // On suppose que les nouvelles couleurs sont à la fin
    val latestColors = colorList.takeLast(5).reversed() // les plus récentes en haut

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Magenta", color = Color(0xFF880E4F)) // rose foncé
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
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

                LazyColumn {
                    items(latestColors) { color ->
                        ColorCard(color) {
                            navController.navigate("detail/${color.name}")
                        }
                    }
                }
            }
        }
    )
}


