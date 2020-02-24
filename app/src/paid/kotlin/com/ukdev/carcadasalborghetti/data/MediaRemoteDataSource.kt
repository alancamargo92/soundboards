package com.ukdev.carcadasalborghetti.data

import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import java.io.InputStream

interface MediaRemoteDataSource {
    suspend fun listMedia(mediaType: MediaType): List<Media>
    suspend fun getStreamLink(mediaId: String): String
    suspend fun download(mediaId: String): InputStream
}