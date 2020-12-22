package com.ukdev.carcadasalborghetti.ui.media

import android.net.Uri
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper

class AudioHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileHelper: FileHelper,
        remoteDataSource: MediaRemoteDataSource,
        ioHelper: IOHelper
) : PaidMediaHandler(mediaHelper, crashReportManager, fileHelper, remoteDataSource, ioHelper) {

    override fun playMedia(link: Uri, media: Media) {
        mediaHelper.playAudio(link)
    }

}