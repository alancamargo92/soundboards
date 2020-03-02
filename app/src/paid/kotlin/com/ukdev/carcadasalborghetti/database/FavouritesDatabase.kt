package com.ukdev.carcadasalborghetti.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ukdev.carcadasalborghetti.model.Media

@Dao
interface FavouritesDatabase {

    @Insert
    suspend fun insert(media: Media)

    @Query("SELECT COUNT() FROM Media WHERE id = :mediaId")
    suspend fun count(mediaId: String): Int

    @Query("SELECT * FROM Media ORDER BY title")
    fun getFavourites(): LiveData<List<Media>>

    @Delete
    suspend fun delete(media: Media)

}