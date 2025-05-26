package com.example.magenta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.magenta.model.ColorInfo

@Composable
fun ColorDetailScreen(color: ColorInfo) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(android.graphics.Color.parseColor(color.hex)))
        )

        Spacer(Modifier.height(16.dp))

        Text(text = "Nom : ${color.name}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Code HEX : ${color.hex}")
        Text(text = "Code RGB : ${color.rgb.toList()}")

        Spacer(Modifier.height(24.dp))

        var isFavorite by remember { mutableStateOf(color.isFavorite) }

        Button(
            onClick = { isFavorite = !isFavorite },
        ) {
            Text(if (isFavorite) "Retirer des favoris ❤️" else "Ajouter aux favoris 🤍")
        }
    }
}
