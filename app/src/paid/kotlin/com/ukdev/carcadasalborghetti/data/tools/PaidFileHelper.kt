package com.ukdev.carcadasalborghetti.data.tools

import com.ukdev.carcadasalborghetti.domain.model.Media
import java.io.File

interface PaidFileHelper : FileHelper {
    suspend fun shareFile(file: File, media: Media)
}