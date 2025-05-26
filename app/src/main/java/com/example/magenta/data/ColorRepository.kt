package com.example.magenta.data

import com.example.magenta.model.ColorEntity

class ColorRepository(private val dao: ColorDao) {
    suspend fun insert(color: ColorEntity) = dao.insertColor(color)
    suspend fun getAll() = dao.getAllColors()
    suspend fun search(query: String) = dao.searchColors("%$query%")
    suspend fun delete(color: ColorEntity) = dao.deleteColor(color)
}
