package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.view.ViewLayer

class AudioHandler(
        context: Context,
        callback: MediaCallback,
        view: ViewLayer,
        crashReportManager: CrashReportManager,
        apiProvider: ApiProvider
) : PaidMediaHandler(context, callback, view, crashReportManager, apiProvider) {

    private var mediaPlayer: MediaPlayer? = null

    override fun stop() {
        mediaPlayer?.stop()
        callback.onStopPlayback()
    }

    override fun isPlaying() = mediaPlayer?.isPlaying ?: false

    override fun playMedia(link: String, title: String) {
        initialiseMediaPlayer(link)
    }

    private fun initialiseMediaPlayer(mediaLink: String) {
        mediaPlayer?.release()
        mediaPlayer = createMediaPlayer(Uri.parse(mediaLink))
    }

}