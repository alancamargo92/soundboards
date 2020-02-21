package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

abstract class MediaHandler(
        protected val context: Context,
        protected val callback: MediaCallback,
        protected val crashReportManager: CrashReportManager
) {

    abstract suspend fun play(media: Media)
    abstract suspend fun share(media: Media, mediaType: MediaType)
    abstract fun stop()
    abstract fun isPlaying(): Boolean

    protected fun createMediaPlayer(uri: Uri): MediaPlayer {
        return MediaPlayer.create(context, uri).apply {
            if (isPlaying) {
                stop()
                callback.onStopPlayback()
                start()
                callback.onStartPlayback()
            } else {
                start()
                callback.onStartPlayback()
            }

            setOnCompletionListener { callback.onStopPlayback() }
        }
    }

}