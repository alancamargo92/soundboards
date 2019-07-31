package com.ukdev.carcadasalborghetti.handlers

import android.media.MediaPlayer
import android.net.Uri
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media

class AudioHandler(callback: MediaCallback) : PaidMediaHandler(callback) {

    private var mediaPlayer: MediaPlayer? = null

    override fun stop() {
        mediaPlayer?.stop()
        callback.onStopPlayback()
    }

    override fun share(media: Media) {

    }

    override fun isPlaying() = mediaPlayer?.isPlaying ?: false

    override fun onLinkReady(link: String) {
        initialiseMediaPlayer(link)
    }

    override fun onError() {

    }

    private fun initialiseMediaPlayer(mediaLink: String) {
        mediaPlayer?.release()
        mediaPlayer = createMediaPlayer(Uri.parse(mediaLink))
    }

}