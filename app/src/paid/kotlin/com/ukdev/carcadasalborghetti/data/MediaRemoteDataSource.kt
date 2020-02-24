package com.ukdev.carcadasalborghetti.data

import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType

interface MediaRemoteDataSource {
    suspend fun listMedia(mediaType: MediaType): List<Media>
}