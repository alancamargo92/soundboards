package com.ukdev.carcadasalborghetti.data.remote

import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Media
import java.io.File

interface MediaRemoteDataSource {

    suspend fun getMediaList(mediaType: MediaType): List<Media>

    suspend fun download(media: Media, destinationFile: File): File
}
