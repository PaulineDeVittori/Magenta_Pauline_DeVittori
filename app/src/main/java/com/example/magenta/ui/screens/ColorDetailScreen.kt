package com.example.magenta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.magenta.model.ColorEntity
import com.example.magenta.model.ColorInfo
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.magenta.viewmodel.ColorViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ColorDetailScreen(
    colorName: String,
    viewModel: ColorViewModel = viewModel(),
    navController: NavHostController
) {
    var color by remember { mutableStateOf<ColorEntity?>(null) }

    // Charger la couleur à partir du ViewModel (Firestore)
    LaunchedEffect(colorName) {
        viewModel.getColorByName(colorName) {
            color = it
        }
    }

    color?.let {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Magenta", color = Color(0xFF8B008B)) // Rose foncé
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
                        .background(Color(android.graphics.Color.parseColor(it.hex)))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Nom : ${it.name}", style = MaterialTheme.typography.headlineSmall)
                Text("Code HEX : ${it.hex}")
                Text("Code RGB : (${it.red}, ${it.green}, ${it.blue})")
            }
        }
    } ?: run {
        // Afficher un indicateur de chargement
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

