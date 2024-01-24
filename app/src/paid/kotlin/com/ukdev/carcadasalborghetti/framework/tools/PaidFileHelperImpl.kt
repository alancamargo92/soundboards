package com.ukdev.carcadasalborghetti.framework.tools

import android.content.Context
import com.ukdev.carcadasalborghetti.domain.entities.Media
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class PaidFileHelperImpl @Inject constructor(
    @ApplicationContext context: Context
) : PaidFileHelper, FileHelperImpl(context) {

    override suspend fun shareFile(file: File, media: Media) {
        val uri = getFileUri(file)
        shareFile(uri, media)
    }
}