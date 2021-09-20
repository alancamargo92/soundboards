package com.ukdev.carcadasalborghetti.framework.tools

import android.net.Uri
import com.dropbox.core.DbxDownloader
import com.dropbox.core.v2.files.FileMetadata
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import java.io.InputStream

interface FileHelper {
    suspend fun listFiles(mediaType: MediaType): List<Media>
    suspend fun getFileUri(fileName: String): Uri
    suspend fun getFileUri(downloader: DbxDownloader<FileMetadata>, media: Media): Uri
    suspend fun shareFile(uri: Uri, media: Media)
    suspend fun shareFile(downloader: DbxDownloader<FileMetadata>, media: Media)
    suspend fun getByteStream(uri: Uri): InputStream?
    suspend fun deleteAll()
}