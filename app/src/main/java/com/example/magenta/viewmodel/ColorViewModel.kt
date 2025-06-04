package com.example.magenta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.magenta.data.FirebaseColorRepository
import com.example.magenta.model.ColorEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ColorViewModel : ViewModel() {

    private val repository = FirebaseColorRepository()

    private val _colors = MutableStateFlow<List<ColorEntity>>(emptyList())
    val colors: StateFlow<List<ColorEntity>> = _colors.asStateFlow()

    private val _searchResults = MutableStateFlow<List<ColorEntity>>(emptyList())
    val searchResults: StateFlow<List<ColorEntity>> = _searchResults.asStateFlow()

    init {
        loadAllColors()
    }

    fun loadAllColors() {
        viewModelScope.launch {
            val all = repository.getAllColors()
            _colors.value = all
            _favorites.value = all.filter { it.isFavorite }
        }
    }

    fun searchColors(query: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchColors(query)
        }
    }

    fun insertAllColors(colorList: List<ColorEntity>) {
        viewModelScope.launch {
            repository.insertColors(colorList)
            loadAllColors() // refresh
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


    private val _favorites = MutableStateFlow<List<ColorEntity>>(emptyList())
    val favorites: StateFlow<List<ColorEntity>> = _favorites.asStateFlow()

    fun toggleFavorite(color: ColorEntity) {
        viewModelScope.launch {
            val updated = color.copy(isFavorite = !color.isFavorite)
            val current = _colors.value.toMutableList()
            val index = current.indexOfFirst { it.name == color.name }
            if (index != -1) {
                current[index] = updated
                _colors.value = current
                _favorites.value = current.filter { it.isFavorite }
                repository.updateFavorite(updated)
            }
        }
    }



}
