package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import android.media.MediaPlayer
import com.ukdev.carcadasalborghetti.listeners.AudioCallback

class AudioHandler(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var audioFileRes: Int? = null

    fun play(audioFileRes: Int, callback: AudioCallback) {
        mediaPlayer?.release()
        this.audioFileRes = audioFileRes

        mediaPlayer = MediaPlayer.create(context, audioFileRes).apply {
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

    fun stop(callback: AudioCallback) {
        mediaPlayer?.stop()
        callback.onStopPlayback()
    }

}