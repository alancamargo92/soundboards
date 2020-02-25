package com.ukdev.carcadasalborghetti.handlers

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.helpers.FileHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

abstract class MediaHandler(
        protected val mediaHelper: MediaHelper,
        protected val crashReportManager: CrashReportManager,
        protected val fileHelper: FileHelper
) {

    abstract suspend fun play(media: Media, mediaType: MediaType)

    abstract suspend fun share(media: Media, mediaType: MediaType)

    fun stop() {
        mediaHelper.stop()
    }

    fun isPlaying(): LiveData<Boolean> {
        return mediaHelper.isPlaying()
    }

}