package com.ukdev.carcadasalborghetti.helpers

import android.net.Uri
import androidx.lifecycle.LiveData

abstract class MediaHelper(protected val videoHelper: VideoHelper) {
    abstract fun playAudio(audioUri: Uri)
    abstract fun playVideo(link: String, title: String)
    abstract fun stop()
    abstract fun isPlaying(): LiveData<Boolean>
}