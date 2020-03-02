package com.ukdev.carcadasalborghetti.data

import com.ukdev.carcadasalborghetti.api.DIR_AUDIO
import com.ukdev.carcadasalborghetti.api.DIR_VIDEO
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class MediaRemoteDataSourceImpl(private val apiProvider: ApiProvider) : MediaRemoteDataSource {

    override suspend fun listMedia(mediaType: MediaType): List<Media> {
        val dir = when (mediaType) {
            MediaType.AUDIO -> DIR_AUDIO
            MediaType.VIDEO -> DIR_VIDEO
            else -> throw IllegalArgumentException("Must be either audio or video")
        }

        val request = MediaRequest(dir)
        val api = apiProvider.getDropboxService()

        return withContext(Dispatchers.IO) {
            api.listMedia(request).entries.map {
                val type = if (it.name.endsWith("mp3"))
                    MediaType.AUDIO
                else
                    MediaType.VIDEO

                Media(it.id, it.name, type)
            }
        }
    }

    override suspend fun download(mediaId: String): InputStream {
        val request = MediaRequest(mediaId)
        val api = apiProvider.getDownloadService()

        return withContext(Dispatchers.IO) {
            api.download(request).byteStream()
        }
    }

}