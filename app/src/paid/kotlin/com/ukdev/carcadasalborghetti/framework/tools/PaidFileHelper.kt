package com.ukdev.carcadasalborghetti.framework.tools

import com.ukdev.carcadasalborghetti.domain.entities.Media
import java.io.File

interface PaidFileHelper : FileHelper {
    suspend fun shareFile(file: File, media: Media)
}