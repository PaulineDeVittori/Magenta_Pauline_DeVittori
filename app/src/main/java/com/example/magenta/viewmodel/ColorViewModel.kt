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
            _colors.value = repository.getAllColors()
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

    fun getColorByName(name: String, onResult: (ColorEntity?) -> Unit) {
        viewModelScope.launch {
            val color = colors.value.find { it.name.equals(name, ignoreCase = true) }
            onResult(color)
        }
    }


}
