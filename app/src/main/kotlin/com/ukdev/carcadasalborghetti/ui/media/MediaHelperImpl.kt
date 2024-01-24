package com.ukdev.carcadasalborghetti.ui.media

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.data.tools.orFalse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MediaHelperImpl @Inject constructor(
    videoHelper: VideoHelper,
    @ApplicationContext private val context: Context
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
        value = mediaPlayer?.isPlaying.orFalse()
    }

    private fun createMediaPlayer(uri: Uri): MediaPlayer {
        return MediaPlayer.create(context, uri).apply {
            if (this?.isPlaying.orFalse()) {
                stop()
                start()
            } else {
                start()
            }

            setOnCompletionListener { isPlayingLiveData.value = false }
        }
    }
}
