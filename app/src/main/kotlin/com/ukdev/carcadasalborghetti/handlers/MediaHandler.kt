package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper

abstract class MediaHandler(
        protected val context: Context,
        protected val crashReportManager: CrashReportManager,
        protected val fileSharingHelper: FileSharingHelper
) {

    protected val isPlayingLiveData = MutableLiveData<Boolean>()

    abstract suspend fun play(media: Media)
    abstract suspend fun share(media: Media, mediaType: MediaType)
    abstract fun stop()
    abstract fun isPlaying(): LiveData<Boolean>

    protected fun createMediaPlayer(uri: Uri): MediaPlayer {
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