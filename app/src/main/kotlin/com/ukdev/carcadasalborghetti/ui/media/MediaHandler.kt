package com.ukdev.carcadasalborghetti.ui.media

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.data.tools.FileHelper

abstract class MediaHandler(
    protected val mediaHelper: MediaHelper,
    protected val logger: Logger,
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