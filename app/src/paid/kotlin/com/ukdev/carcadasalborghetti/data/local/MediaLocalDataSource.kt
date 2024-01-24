package com.ukdev.carcadasalborghetti.data.local

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType

interface MediaLocalDataSource {
    suspend fun listMedia(mediaType: MediaType): List<Media>
    suspend fun clearCache()
}