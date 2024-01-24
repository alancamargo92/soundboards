package com.ukdev.carcadasalborghetti.domain.repository

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    suspend fun getMedia(mediaType: MediaType): List<Media>

    fun getFavourites(): Flow<List<Media>>

    suspend fun saveToFavourites(media: Media)

    suspend fun removeFromFavourites(media: Media)

    suspend fun isSavedToFavourites(media: Media): Boolean
}
