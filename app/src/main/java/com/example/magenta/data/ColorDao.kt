package com.example.magenta.data

import androidx.room.*
import com.example.magenta.model.ColorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    @Query("SELECT * FROM colors ORDER BY name ASC")
    fun getAllColors(): Flow<List<ColorEntity>> // ← plus suspend ici !

    @Query("SELECT * FROM colors WHERE name = :name LIMIT 1")
    suspend fun getColorByName(name: String): ColorEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(colors: List<ColorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ColorEntity)

    @Query("""
        SELECT * FROM colors 
        WHERE name LIKE '%' || :query || '%' 
        OR hex LIKE '%' || :query || '%' 
        OR CAST(red AS TEXT) || ',' || CAST(green AS TEXT) || ',' || CAST(blue AS TEXT) LIKE '%' || :query || '%'
    """)
    suspend fun searchColors(query: String): List<ColorEntity>

    @Delete
    suspend fun deleteColor(color: ColorEntity)
}

