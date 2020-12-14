package com.ukdev.carcadasalborghetti.framework.local

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper

class MediaLocalDataSourceImpl(private val fileHelper: FileHelper) : MediaLocalDataSource {

    override suspend fun listMedia(mediaType: MediaType): List<Media> {
        return fileHelper.listFiles(mediaType)
    }

    override suspend fun clearCache() {
        fileHelper.deleteAll()
    }

}