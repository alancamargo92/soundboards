package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.helpers.FileSharingHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileSharingHelper
) : MediaHandler(mediaHelper, crashReportManager, fileSharingHelper) {

    override suspend fun play(media: Media) {
        try {
            mediaHelper.playAudio(media.uri)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override suspend fun share(media: Media, mediaType: MediaType) {
        try {
            val fileName = "${media.title}.mp3"
            withContext(Dispatchers.IO) {
                fileSharingHelper.getByteStream(media.uri)
            }.let { byteStream ->
                fileSharingHelper.shareFile(byteStream, fileName, mediaType)
            }
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

}