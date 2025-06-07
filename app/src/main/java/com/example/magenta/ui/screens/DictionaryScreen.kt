package com.example.magenta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
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
fun DictionaryScreen(
    navController: NavHostController,
    viewModel: ColorViewModel = viewModel()
) {
    val colorList by viewModel.colors.collectAsState()
    var selectedLetter by remember { mutableStateOf<Char?>(null) }
    val filteredColors = filterColorsByLetter(colorList, selectedLetter)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Magenta",
                        color = Color(0xFF880E4F)
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                LetterFilterBar(selectedLetter) { selectedLetter = it }
                Spacer(modifier = Modifier.height(8.dp))
                ColorList(colors = filteredColors, navController = navController)
            }
        }
    )
}


@Composable
fun LetterFilterBar(selected: Char?, onLetterSelected: (Char?) -> Unit) {
    val letters = ('A'..'P').toList()

    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        item {
            FilterChip(selected == null, "All") { onLetterSelected(null) }
        }
        items(letters) { letter ->
            FilterChip(selected == letter, letter.toString()) { onLetterSelected(letter) }
        }
    }
}

@Composable
fun FilterChip(isSelected: Boolean, text: String, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (isSelected) Color.Gray else Color.LightGray
        ),
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

fun filterColorsByLetter(list: List<ColorEntity>, letter: Char?): List<ColorEntity> {
    return if (letter == null) list else list.filter { it.name.startsWith(letter, ignoreCase = true) }
}

@Composable
fun ColorList(
    colors: List<ColorEntity>,
    navController: NavHostController,
    viewModel: ColorViewModel = viewModel()
) {
    val favorites = viewModel.favorites.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(colors) { color ->
            ColorCard(
                color = color,
                isFavorite = favorites.contains(color.name), // <- correction ici
                onClick = { navController.navigate("detail/${color.name}") },
                onToggleFavorite = { viewModel.toggleFavorite(color) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


