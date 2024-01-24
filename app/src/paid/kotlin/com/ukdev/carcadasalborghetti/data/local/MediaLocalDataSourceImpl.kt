package com.ukdev.carcadasalborghetti.data.local

import com.ukdev.carcadasalborghetti.data.tools.FileHelper
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import javax.inject.Inject

class MediaLocalDataSourceImpl @Inject constructor(
    private val fileHelper: FileHelper
) : MediaLocalDataSource {

    override suspend fun listMedia(mediaType: MediaType): List<Media> {
        return fileHelper.listFiles(mediaType)
    }

    override suspend fun clearCache() {
        fileHelper.deleteAll()
    }

}