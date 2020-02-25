package com.ukdev.carcadasalborghetti.helpers

import android.net.Uri
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import java.io.InputStream

interface FileHelper {
    suspend fun listFiles(mediaType: MediaType): List<Media>
    suspend fun getFileUri(fileName: String): Uri
    suspend fun shareFile(byteStream: InputStream?, fileName: String, mediaType: MediaType)
    suspend fun getByteStream(uri: Uri): InputStream?
    suspend fun deleteAll()
}