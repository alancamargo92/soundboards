package com.ukdev.carcadasalborghetti.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ukdev.carcadasalborghetti.model.Media

@Dao
interface FavouritesDatabase {

    @Insert
    suspend fun insert(media: Media)

    @Query("SELECT * FROM Media")
    suspend fun getFavourites(): List<Media>

    @Delete
    suspend fun delete(media: Media)

}