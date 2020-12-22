package com.ukdev.carcadasalborghetti.ui.media

import com.ukdev.carcadasalborghetti.framework.tools.FileHelper
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileHelper
) : MediaHandler(mediaHelper, crashReportManager, fileSharingHelper) {

    override suspend fun play(media: Media) {
        try {
            mediaHelper.playAudio(media.uri)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override suspend fun share(media: Media) {
        try {
            withContext(Dispatchers.IO) {
                fileHelper.getByteStream(media.uri)
            }.let { byteStream ->
                fileHelper.shareFile(byteStream, media)
            }
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

}