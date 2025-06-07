package com.example.magenta.viewmodel

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.magenta.data.FirebaseColorRepository
import com.example.magenta.model.ColorEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ColorViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(application)
    private val FAVORITES_KEY = "favorite_colors"

    private val repository = FirebaseColorRepository()

    private val _colors = MutableStateFlow<List<ColorEntity>>(emptyList())
    val colors: StateFlow<List<ColorEntity>> = _colors.asStateFlow()

    private val _searchResults = MutableStateFlow<List<ColorEntity>>(emptyList())
    val searchResults: StateFlow<List<ColorEntity>> = _searchResults.asStateFlow()

    private val _favorites = MutableStateFlow<Set<String>>(emptySet()) // noms des couleurs
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    init {
        loadFavoritesFromPrefs()
        loadAllColors()
    }

    private fun loadFavoritesFromPrefs() {
        _favorites.value = prefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    private fun saveFavoritesToPrefs(favs: Set<String>) {
        prefs.edit().putStringSet(FAVORITES_KEY, favs).apply()
    }

    fun loadAllColors() {
        viewModelScope.launch {
            val all = repository.getAllColors()
            _colors.value = all
        }
    }

    fun toggleFavorite(color: ColorEntity) {
        val currentFavs = _favorites.value.toMutableSet()
        val colorName = color.name

        if (currentFavs.contains(colorName)) {
            currentFavs.remove(colorName)
        } else {
            currentFavs.add(colorName)
        }

        _favorites.value = currentFavs
        saveFavoritesToPrefs(currentFavs)
    }

    fun searchColors(query: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchColors(query)
        }
    }

    fun insertAllColors(colorList: List<ColorEntity>) {
        viewModelScope.launch {
            repository.insertColors(colorList)
            loadAllColors()
        }
    }

    private val colorCache = mutableMapOf<String, ColorEntity>()

    fun getColorByName(name: String, onResult: (ColorEntity?) -> Unit) {
        viewModelScope.launch {
            val key = name.lowercase()
            val cached = colorCache[key]
            if (cached != null) {
                onResult(cached)
            } else {
                val localColor = colors.value.find { it.name.equals(name, ignoreCase = true) }
                if (localColor != null) {
                    colorCache[key] = localColor
                    onResult(localColor)
                } else {
                    val remoteColor = repository.getColorByName(name)
                    if (remoteColor != null) colorCache[key] = remoteColor
                    onResult(remoteColor)
                }
            }
        }
    }

    fun getRandomColor(onSuccess: (ColorEntity) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val allColors = repository.getAllColors()
                if (allColors.isNotEmpty()) {
                    val randomColor = allColors.random()
                    onSuccess(randomColor)
                } else {
                    onError("Aucune couleur disponible")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Erreur inconnue")
            }
        }
    }
}

