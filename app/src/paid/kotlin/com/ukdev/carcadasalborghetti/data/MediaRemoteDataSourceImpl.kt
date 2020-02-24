package com.ukdev.carcadasalborghetti.data

import com.ukdev.carcadasalborghetti.api.DIR_AUDIO
import com.ukdev.carcadasalborghetti.api.DIR_VIDEO
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType

class MediaRemoteDataSourceImpl(private val apiProvider: ApiProvider) : MediaRemoteDataSource {

    override suspend fun listMedia(mediaType: MediaType): List<Media> {
        val dir = if (mediaType == MediaType.AUDIO)
            DIR_AUDIO
        else
            DIR_VIDEO
        val request = MediaRequest(dir)
        val api = apiProvider.getDropboxService()

        return api.listMedia(request).entries
    }

}