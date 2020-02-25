package com.ukdev.carcadasalborghetti.data

import com.ukdev.carcadasalborghetti.helpers.FileHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType

class MediaLocalDataSourceImpl(private val fileHelper: FileHelper) : MediaLocalDataSource {

    override suspend fun listMedia(mediaType: MediaType): List<Media> {
        return fileHelper.listFiles(mediaType)
    }

    override suspend fun load(mediaId: String): Media {
        return Media("", "")
    }

    override suspend fun clearCache() {
        fileHelper.deleteAll()
    }

}