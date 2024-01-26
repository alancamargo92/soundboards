package com.ukdev.carcadasalborghetti.data.remote

import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import java.io.File

interface MediaRemoteDataSourceV2 {

    suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2>

    suspend fun download(media: MediaV2): File
}
