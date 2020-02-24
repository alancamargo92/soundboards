package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper

class AudioHandler(
        context: Context,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileSharingHelper,
        remoteDataSource: MediaRemoteDataSource
) : PaidMediaHandler(context, crashReportManager, fileSharingHelper, remoteDataSource) {

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