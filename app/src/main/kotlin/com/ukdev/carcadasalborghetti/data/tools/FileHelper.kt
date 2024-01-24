package com.ukdev.carcadasalborghetti.data.tools

import android.net.Uri
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import java.io.File
import java.io.InputStream

interface FileHelper {
    suspend fun listFiles(mediaType: MediaType): List<Media>
    suspend fun getFileUri(fileName: String): Uri
    suspend fun getFileUri(inputStream: InputStream?, media: Media): Uri
    suspend fun shareFile(uri: Uri, media: Media)
    suspend fun shareFile(inputStream: InputStream?, media: Media)
    suspend fun getByteStream(uri: Uri): InputStream?
    suspend fun deleteAll()

    suspend fun createFile(fileName: String, mediaType: MediaType): File
}