package com.ukdev.carcadasalborghetti.framework.media

import com.ukdev.carcadasalborghetti.framework.tools.FileHelper
import com.ukdev.carcadasalborghetti.framework.media.MediaHelper
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.framework.media.MediaHandler

class VideoHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileHelper
) : MediaHandler(mediaHelper, crashReportManager, fileSharingHelper) {

    override suspend fun play(media: Media) { }

    override suspend fun share(media: Media) { }

}