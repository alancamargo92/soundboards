package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper

class VideoHandler(
        context: Context,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileSharingHelper
) : MediaHandler(context, crashReportManager, fileSharingHelper) {

    override suspend fun play(media: Media) { }

    override fun stop() { }

    override suspend fun share(media: Media, mediaType: MediaType) { }

    override fun isPlaying() = isPlayingLiveData.apply {
        value = false
    }

}