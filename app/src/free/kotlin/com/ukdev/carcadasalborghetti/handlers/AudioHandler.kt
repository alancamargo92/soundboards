package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioHandler(
        context: Context,
        callback: MediaCallback,
        crashReportManager: CrashReportManager
) : MediaHandler(context, callback, crashReportManager) {

    private var mediaPlayer: MediaPlayer? = null

    override suspend fun play(media: Media) {
        try {
            mediaPlayer?.release()
            mediaPlayer = createMediaPlayer(media.uri)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override fun stop() {
        mediaPlayer?.stop()
        callback.onStopPlayback()
    }

    override suspend fun share(media: Media, mediaType: MediaType) {
        try {
            val fileName = "${media.title}.mp3"
            withContext(Dispatchers.IO) {
                context.contentResolver.openInputStream(media.uri)
            }.use { byteStream ->
                FileSharingHelper(context).shareFile(byteStream, fileName, mediaType)
            }
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override fun isPlaying() = mediaPlayer?.isPlaying ?: false

}