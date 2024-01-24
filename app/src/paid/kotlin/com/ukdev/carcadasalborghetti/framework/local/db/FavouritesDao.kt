package com.ukdev.carcadasalborghetti.framework.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ukdev.carcadasalborghetti.domain.model.Media

@Dao
interface FavouritesDao {

    @Insert(entity = Media::class)
    suspend fun insert(media: Media)

    @Query("SELECT COUNT() FROM Media WHERE id = :mediaId")
    suspend fun count(mediaId: String): Int

    @Query("SELECT * FROM Media ORDER BY title")
    fun getFavourites(): LiveData<List<Media>>

    @Delete(entity = Media::class)
    suspend fun delete(media: Media)

}