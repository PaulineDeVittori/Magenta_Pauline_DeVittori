package com.example.magenta.model

data class ColorInfo(
    val name: String,
    val hex: String,
    val rgb: Triple<Int, Int, Int>,
    var isFavorite: Boolean = false
)
