package com.ukdev.carcadasalborghetti.framework.tools

import android.content.Context
import com.ukdev.carcadasalborghetti.domain.entities.Media
import java.io.File

class PaidFileHelperImpl(context: Context) : PaidFileHelper, FileHelperImpl(context) {

    override suspend fun shareFile(file: File, media: Media) {
        val uri = getFileUri(file)
        shareFile(uri, media)
    }
}