package com.ukdev.carcadasalborghetti.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ukdev.carcadasalborghetti.data.model.DbMedia
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDaoV2 {

    @Insert(entity = DbMedia::class)
    suspend fun insert(media: DbMedia)

    @Query("SELECT COUNT() FROM DbMedia WHERE id = :mediaId")
    suspend fun count(mediaId: String): Int

    @Query("SELECT * FROM DbMedia ORDER BY title")
    fun getFavourites(): Flow<List<DbMedia>>

    @Delete(entity = DbMedia::class)
    suspend fun delete(media: DbMedia)
}
