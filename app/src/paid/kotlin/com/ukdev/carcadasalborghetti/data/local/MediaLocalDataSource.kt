package com.ukdev.carcadasalborghetti.data.local

import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType

interface MediaLocalDataSource {
    suspend fun listMedia(mediaType: MediaType): List<Media>
    suspend fun clearCache()
}