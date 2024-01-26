package com.ukdev.carcadasalborghetti.domain.repository

import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import kotlinx.coroutines.flow.Flow

interface MediaRepositoryV2 {

    suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2>

    fun getFavourites(): Flow<List<MediaV2>>

    suspend fun saveToFavourites(media: MediaV2)

    suspend fun removeFromFavourites(media: MediaV2)

    suspend fun isSavedToFavourites(media: MediaV2): Boolean
}
