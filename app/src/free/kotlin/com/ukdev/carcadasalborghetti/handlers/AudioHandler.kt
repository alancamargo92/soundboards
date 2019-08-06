package com.ukdev.carcadasalborghetti.handlers

import android.media.MediaPlayer
import com.crashlytics.android.Crashlytics
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.FileUtils
import org.koin.core.KoinComponent
import java.io.IOException

class AudioHandler(callback: MediaCallback) : MediaHandler(callback), KoinComponent {

    private var mediaPlayer: MediaPlayer? = null

    override fun play(media: Media) {
        mediaPlayer?.release()
        mediaPlayer = createMediaPlayer(media.uri)
    }

    override fun stop() {
        mediaPlayer?.stop()
        callback.onStopPlayback()
    }

    override fun share(media: Media, mediaType: MediaType) {
        val fileUtils = FileUtils(context)
        val file = try {
            val fileName = "${media.title}.mp3"
            val byteStream = context.contentResolver.openInputStream(media.uri)
            fileUtils.getFile(byteStream, fileName)
        } catch (ex: IOException) {
            Crashlytics.log("Error creating file for ${media.title}")
            Crashlytics.logException(ex)
            return
        }

        val uri = fileUtils.getUri(file)
        fileUtils.shareFile(uri, mediaType)
    }

    override fun isPlaying() = mediaPlayer?.isPlaying ?: false

}