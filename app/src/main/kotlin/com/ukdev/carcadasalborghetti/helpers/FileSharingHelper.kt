package com.ukdev.carcadasalborghetti.helpers

import android.net.Uri
import com.ukdev.carcadasalborghetti.model.MediaType
import java.io.InputStream

interface FileSharingHelper {
    suspend fun shareFile(byteStream: InputStream?, fileName: String, mediaType: MediaType)
    suspend fun getByteStream(uri: Uri): InputStream?
}