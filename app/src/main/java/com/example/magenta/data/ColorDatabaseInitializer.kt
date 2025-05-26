package com.example.magenta.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.magenta.model.ColorEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "app_preferences")

object ColorDatabaseInitializer {

    private val IS_DB_FILLED = booleanPreferencesKey("is_db_filled")

    fun initialize(context: Context, colorDao: ColorDao) {
        CoroutineScope(Dispatchers.IO).launch {
            val prefs = context.dataStore.data.first()
            val isAlreadyFilled = prefs[IS_DB_FILLED] ?: false

            if (!isAlreadyFilled) {
                // Pré-remplissage
                colorDao.insertAll(colorList.map {
                    ColorEntity(
                        name = it.name,
                        hex = it.hex,
                        red = it.rgb.first,
                        green = it.rgb.second,
                        blue = it.rgb.third
                    )                })

                // Marquer comme fait
                context.dataStore.edit { it[IS_DB_FILLED] = true }
            }
        }
    }
}
