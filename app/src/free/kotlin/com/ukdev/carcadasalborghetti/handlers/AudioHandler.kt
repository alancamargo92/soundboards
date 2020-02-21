package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import com.crashlytics.android.Crashlytics
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileUtils
import com.ukdev.carcadasalborghetti.view.ViewLayer
import java.io.IOException

class AudioHandler(
        context: Context,
        callback: MediaCallback,
        view: ViewLayer,
        crashReportManager: CrashReportManager
) : MediaHandler(context, callback, view, crashReportManager) {

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

    override fun share(media: Media, mediaType: MediaType) {
        val fileUtils = FileUtils(context)
        try {
            val fileName = "${media.title}.mp3"
            val byteStream = context.contentResolver.openInputStream(media.uri)
            val file = fileUtils.getFile(byteStream, fileName)
            val uri = fileUtils.getUri(file)
            fileUtils.shareFile(uri, mediaType)
        } catch (ex: IOException) {
            Crashlytics.log("Error creating file for ${media.title}")
            Crashlytics.logException(ex)
            return
        } finally {
            view.notifyItemReady()
        }
    }

    override fun isPlaying() = mediaPlayer?.isPlaying ?: false

}