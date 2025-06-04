package com.example.magenta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
fun RandomColorScreen(
    navController: NavHostController,
    viewModel: ColorViewModel = viewModel()
) {
    var randomColor by remember { mutableStateOf<ColorEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun loadRandomColor() {
        isLoading = true
        errorMessage = null
        viewModel.getRandomColor(
            onSuccess = {
                randomColor = it
                isLoading = false
            },
            onError = { err ->
                errorMessage = err
                isLoading = false
            }
        )
    }

    LaunchedEffect(Unit) {
        loadRandomColor()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Couleur Aléatoire", color = Color(0xFF880E4F)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { loadRandomColor() }) {
                Text("↻")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    errorMessage != null -> Text("Erreur: $errorMessage")
                    randomColor != null -> {
                        val color = randomColor!!
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(Color(android.graphics.Color.parseColor(color.hex)))
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Nom : ${color.name}", style = MaterialTheme.typography.headlineSmall)
                        Text("Code HEX : ${color.hex}")
                        Text("Code RGB : (${color.red}, ${color.green}, ${color.blue})")
                    }
                }
            }
        }
    )
}
