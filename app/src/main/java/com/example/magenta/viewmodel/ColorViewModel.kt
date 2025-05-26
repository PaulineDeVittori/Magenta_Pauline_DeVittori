package com.example.magenta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.magenta.data.AppDatabase
import com.example.magenta.model.ColorEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ColorViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).colorDao()

    val colors: StateFlow<List<ColorEntity>> = dao.getAllColors()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertAllColors(colorList: List<ColorEntity>) {
        viewModelScope.launch {
            dao.insertAll(colorList)
        }
    }
}

