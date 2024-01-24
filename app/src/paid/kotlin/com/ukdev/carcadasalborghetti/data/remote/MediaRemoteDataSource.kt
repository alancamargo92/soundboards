package com.ukdev.carcadasalborghetti.data.remote

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import java.io.File

interface MediaRemoteDataSource {
    suspend fun listMedia(mediaType: MediaType): List<Media>
    suspend fun download(mediaId: String): File
}