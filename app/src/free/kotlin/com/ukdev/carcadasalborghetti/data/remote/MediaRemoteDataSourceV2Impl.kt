package com.ukdev.carcadasalborghetti.data.remote

import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import java.io.File
import javax.inject.Inject

class MediaRemoteDataSourceV2Impl @Inject constructor() : MediaRemoteDataSourceV2 {

    override suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2> {
        error("Free version only uses local files")
    }

    override suspend fun download(media: MediaV2, destinationFile: File): File {
        error("Free version only uses local files")
    }
}
