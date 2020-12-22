package com.ukdev.carcadasalborghetti.ui.media

import android.net.Uri
import androidx.lifecycle.LiveData

abstract class MediaHelper(protected val videoHelper: VideoHelper) {
    abstract fun playAudio(audioUri: Uri)
    abstract fun playVideo(link: Uri, title: String)
    abstract fun stop()
    abstract fun isPlaying(): LiveData<Boolean>
}