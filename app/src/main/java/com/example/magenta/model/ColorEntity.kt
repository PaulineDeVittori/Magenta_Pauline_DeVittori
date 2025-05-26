package com.example.magenta.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class ColorEntity(
    @PrimaryKey val name: String,
    val hex: String,
    val red: Int,
    val green: Int,
    val blue: Int
)

