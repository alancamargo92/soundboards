package com.ukdev.carcadasalborghetti.data

import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType

interface MediaLocalDataSource {
    suspend fun listMedia(mediaType: MediaType): List<Media>
    suspend fun load(mediaId: String): Media
    suspend fun clearCache()
}