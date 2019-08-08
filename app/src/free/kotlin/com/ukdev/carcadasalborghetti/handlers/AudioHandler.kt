package com.ukdev.carcadasalborghetti.handlers

import android.media.MediaPlayer
import com.crashlytics.android.Crashlytics
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.FileUtils
import com.ukdev.carcadasalborghetti.view.ViewLayer
import java.io.IOException

class AudioHandler(callback: MediaCallback, view: ViewLayer) : MediaHandler(callback, view) {

    private var mediaPlayer: MediaPlayer? = null

    override fun play(media: Media) {
        view.notifyItemReady()
        mediaPlayer?.release()
        mediaPlayer = createMediaPlayer(media.uri)
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