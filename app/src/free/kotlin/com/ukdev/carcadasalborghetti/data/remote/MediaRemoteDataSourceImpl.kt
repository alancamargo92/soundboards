package com.ukdev.carcadasalborghetti.data.remote

import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Media
import java.io.File
import javax.inject.Inject

class MediaRemoteDataSourceImpl @Inject constructor() : MediaRemoteDataSource {

    override suspend fun getMediaList(mediaType: MediaType): List<Media> {
        error("Free version only uses local files")
    }

    override suspend fun download(media: Media, destinationFile: File): File {
        error("Free version only uses local files")
    }
}
