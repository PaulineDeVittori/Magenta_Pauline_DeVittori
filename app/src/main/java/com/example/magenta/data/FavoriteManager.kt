package com.example.magenta.data

import android.content.Context
import android.content.SharedPreferences

class FavoriteManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

    private val FAVORITES_KEY = "favorite_colors"

    fun getFavorites(): Set<String> {
        return prefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    fun isFavorite(colorName: String): Boolean {
        return getFavorites().contains(colorName)
    }

    fun addFavorite(colorName: String) {
        val updated = getFavorites().toMutableSet().apply { add(colorName) }
        prefs.edit().putStringSet(FAVORITES_KEY, updated).apply()
    }

    fun removeFavorite(colorName: String) {
        val updated = getFavorites().toMutableSet().apply { remove(colorName) }
        prefs.edit().putStringSet(FAVORITES_KEY, updated).apply()
    }

    fun toggleFavorite(colorName: String) {
        if (isFavorite(colorName)) {
            removeFavorite(colorName)
        } else {
            addFavorite(colorName)
        }
    }
}
