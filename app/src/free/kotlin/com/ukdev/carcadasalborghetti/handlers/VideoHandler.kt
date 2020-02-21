package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class VideoHandler(
        context: Context,
        callback: MediaCallback,
        crashReportManager: CrashReportManager
) : MediaHandler(context, callback, crashReportManager) {
    override suspend fun play(media: Media) { }
    override fun stop() { }
    override suspend fun share(media: Media, mediaType: MediaType) { }
    override fun isPlaying() = false
}