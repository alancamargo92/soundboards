package com.ukdev.carcadasalborghetti.data.remote

import com.dropbox.core.DbxDownloader
import com.dropbox.core.v2.files.FileMetadata
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType

interface MediaRemoteDataSource {
    suspend fun listMedia(mediaType: MediaType): List<Media>
    suspend fun download(mediaId: String): DbxDownloader<FileMetadata>
}