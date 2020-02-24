package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.helpers.FileSharingHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class VideoHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileSharingHelper
) : MediaHandler(mediaHelper, crashReportManager, fileSharingHelper) {

    override suspend fun play(media: Media) { }

    override suspend fun share(media: Media, mediaType: MediaType) { }

}