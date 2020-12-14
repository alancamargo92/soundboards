package com.ukdev.carcadasalborghetti.framework.media

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager

abstract class MediaHandler(
        protected val mediaHelper: MediaHelper,
        protected val crashReportManager: CrashReportManager,
        protected val fileHelper: FileHelper
) {

    abstract suspend fun play(media: Media)

    abstract suspend fun share(media: Media)

    fun stop() {
        mediaHelper.stop()
    }

    fun isPlaying(): LiveData<Boolean> {
        return mediaHelper.isPlaying()
    }

}