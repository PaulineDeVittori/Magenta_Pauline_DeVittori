package com.example.magenta.data

import com.example.magenta.model.ColorEntity

class ColorRepository(private val dao: ColorDao) {
    suspend fun insertColor(color: ColorEntity) {
        dao.insertColor(color)
    }

    suspend fun getAll() = dao.getAllColors()

    suspend fun searchColors(query: String): List<ColorEntity> {
        return dao.searchColors(query)
    }
    
    suspend fun deleteColor(color: ColorEntity) {
        dao.deleteColor(color)
    }
}
