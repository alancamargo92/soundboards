package com.ukdev.carcadasalborghetti.data.local

import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Media
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MediaLocalDataSource {

    suspend fun getMediaList(mediaType: MediaType): List<Media>

    fun clearCache()

    fun getFavourites(): Flow<List<Media>>

    suspend fun saveToFavourites(media: Media)

    suspend fun removeFromFavourites(media: Media)

    suspend fun isSavedToFavourites(media: Media): Boolean

    fun createFile(media: Media): File

    fun getFileUri(file: File): String
}
