package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioHandler(
        context: Context,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileSharingHelper
) : MediaHandler(context, crashReportManager, fileSharingHelper) {

    private var mediaPlayer: MediaPlayer? = null

    override suspend fun play(media: Media) {
        try {
            mediaPlayer?.release()
            mediaPlayer = createMediaPlayer(media.uri)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override fun stop() {
        mediaPlayer?.stop()
        isPlayingLiveData.value = false
    }

    override suspend fun share(media: Media, mediaType: MediaType) {
        try {
            val fileName = "${media.title}.mp3"
            withContext(Dispatchers.IO) {
                context.contentResolver.openInputStream(media.uri)
            }.let { byteStream ->
                fileSharingHelper.shareFile(byteStream, fileName, mediaType)
            }
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override fun isPlaying() = isPlayingLiveData.apply {
        value = mediaPlayer?.isPlaying ?: false
    }

}