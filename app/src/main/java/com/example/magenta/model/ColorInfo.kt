package com.example.magenta.model

data class ColorInfo(
    val name: String,
    val hex: String,
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
    var isFavorite: Boolean = false
)
