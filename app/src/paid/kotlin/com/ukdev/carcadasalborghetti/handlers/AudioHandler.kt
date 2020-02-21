package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class AudioHandler(
        context: Context,
        crashReportManager: CrashReportManager,
        apiProvider: ApiProvider
) : PaidMediaHandler(context, crashReportManager, apiProvider) {

    private var mediaPlayer: MediaPlayer? = null

    override fun stop() {
        mediaPlayer?.stop()
        isPlayingLiveData.value = false
    }

    override fun isPlaying() = isPlayingLiveData.apply {
        value = mediaPlayer?.isPlaying ?: false
    }

    override fun playMedia(link: String, title: String) {
        initialiseMediaPlayer(link)
    }

    private fun initialiseMediaPlayer(mediaLink: String) {
        mediaPlayer?.release()
        mediaPlayer = createMediaPlayer(Uri.parse(mediaLink))
    }

}