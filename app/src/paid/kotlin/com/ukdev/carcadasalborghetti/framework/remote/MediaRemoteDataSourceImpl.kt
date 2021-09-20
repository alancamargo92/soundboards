package com.ukdev.carcadasalborghetti.framework.remote

import com.dropbox.core.DbxDownloader
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.framework.remote.api.DIR_AUDIO
import com.ukdev.carcadasalborghetti.framework.remote.api.DIR_VIDEO

private const val EXTENSION_MP3 = "mp3"

class MediaRemoteDataSourceImpl(
    private val client: DbxClientV2
) : MediaRemoteDataSource {

    override suspend fun listMedia(mediaType: MediaType): List<Media> {
        val dir = when (mediaType) {
            MediaType.AUDIO -> DIR_AUDIO
            MediaType.VIDEO -> DIR_VIDEO
            else -> throw IllegalArgumentException("Must be either audio or video")
        }

        return client.files().listFolder(dir).entries.map {
            val type = if (it.name.endsWith(EXTENSION_MP3))
                MediaType.AUDIO
            else
                MediaType.VIDEO

            Media((it as FileMetadata).id, it.name, type)
        }
    }

    override suspend fun download(mediaId: String): DbxDownloader<FileMetadata> {
        return client.files().download(mediaId)
    }

}