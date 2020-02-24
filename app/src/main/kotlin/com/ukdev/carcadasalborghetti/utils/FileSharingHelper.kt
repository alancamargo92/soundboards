package com.ukdev.carcadasalborghetti.utils

import com.ukdev.carcadasalborghetti.model.MediaType
import java.io.InputStream

interface FileSharingHelper {
    suspend fun shareFile(byteStream: InputStream?, fileName: String, mediaType: MediaType)
}