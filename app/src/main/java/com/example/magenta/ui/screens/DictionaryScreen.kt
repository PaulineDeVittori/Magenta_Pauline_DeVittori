package com.example.magenta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.magenta.viewmodel.ColorViewModel

@Composable
fun DictionaryScreen(
    navController: NavHostController,
    viewModel: ColorViewModel = viewModel()
) {
    val colorList by viewModel.colors.collectAsState()
    var selectedLetter by remember { mutableStateOf<Char?>(null) }

    val filteredColors = filterColorsByLetter(colorList, selectedLetter)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        LetterFilterBar(selectedLetter) { selectedLetter = it }

        Spacer(modifier = Modifier.height(8.dp))

        ColorList(colors = filteredColors, navController = navController)
    }
}

@Composable
fun LetterFilterBar(selected: Char?, onLetterSelected: (Char?) -> Unit) {
    val letters = ('A'..'Z').toList()

    LazyRow {
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
fun ColorList(colors: List<ColorEntity>, navController: NavHostController) {
    LazyColumn {
        items(colors) { color ->
            ColorCard(color = color) {
                navController.navigate("detail/${color.name}")
            }
        }
    }
}

@Composable
fun ColorCard(color: ColorEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(android.graphics.Color.parseColor(color.hex)))
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(text = color.name)
                Text(text = "HEX: ${color.hex}")
                Text(text = "RGB: ${color.red}, ${color.green}, ${color.blue}")
            }
        }
    }
}
