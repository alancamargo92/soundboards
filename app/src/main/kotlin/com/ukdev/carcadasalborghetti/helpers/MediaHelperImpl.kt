package com.ukdev.carcadasalborghetti.helpers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MediaHelperImpl(
        videoHelper: VideoHelper,
        private val context: Context
) : MediaHelper(videoHelper) {

    private val isPlayingLiveData = MutableLiveData<Boolean>()

    private var mediaPlayer: MediaPlayer? = null

    override fun playAudio(audioUri: Uri) {
        mediaPlayer?.release()
        mediaPlayer = createMediaPlayer(audioUri)
        isPlayingLiveData.postValue(true)
    }

    override fun playVideo(link: Uri, title: String) {
        videoHelper.playVideo(link, title)
    }

    override fun stop() {
        mediaPlayer?.stop()
        isPlayingLiveData.postValue(false)
    }

    override fun isPlaying(): LiveData<Boolean> = isPlayingLiveData.apply {
        value = mediaPlayer?.isPlaying ?: false
    }

    private fun createMediaPlayer(uri: Uri): MediaPlayer {
        return MediaPlayer.create(context, uri).apply {
            if (isPlaying) {
                stop()
                start()
            } else {
                start()
            }

            setOnCompletionListener { isPlayingLiveData.value = false }
        }
    }

}