package com.ukdev.carcadasalborghetti.framework.tools

import android.net.Uri
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import java.io.InputStream

interface FileHelper {
    suspend fun listFiles(mediaType: MediaType): List<Media>
    suspend fun getFileUri(fileName: String): Uri
    suspend fun getFileUri(inputStream: InputStream?, media: Media): Uri
    suspend fun shareFile(uri: Uri, media: Media)
    suspend fun shareFile(inputStream: InputStream?, media: Media)
    suspend fun getByteStream(uri: Uri): InputStream?
    suspend fun deleteAll()
}