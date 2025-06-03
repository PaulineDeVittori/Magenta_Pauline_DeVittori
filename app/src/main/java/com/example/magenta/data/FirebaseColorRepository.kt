package com.example.magenta.data

import com.example.magenta.model.ColorEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseColorRepository {
    private val db = FirebaseFirestore.getInstance()
    private val colorCollection = db.collection("colors")

    suspend fun getAllColors(): List<ColorEntity> {
        return try {
            val snapshot = colorCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(ColorEntity::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun searchColors(query: String): List<ColorEntity> {
        val all = getAllColors()
        return all.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.hex.contains(query, ignoreCase = true) ||
                    "${it.red},${it.green},${it.blue}".contains(query)
        }
    }

    suspend fun insertColors(colors: List<ColorEntity>) {
        colors.forEach {
            colorCollection.document(it.name).set(it).await()
        }
    }
}
