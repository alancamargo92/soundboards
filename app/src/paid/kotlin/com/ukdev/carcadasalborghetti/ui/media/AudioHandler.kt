package com.ukdev.carcadasalborghetti.ui.media

import android.net.Uri
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.framework.tools.PaidFileHelper

class AudioHandler(
    mediaHelper: MediaHelper,
    crashReportManager: CrashReportManager,
    remoteDataSource: MediaRemoteDataSource,
    ioHelper: IOHelper,
    paidFileHelper: PaidFileHelper
) : PaidMediaHandler(
    mediaHelper,
    crashReportManager,
    remoteDataSource,
    ioHelper,
    paidFileHelper
) {

    override fun playMedia(link: Uri, media: Media) {
        mediaHelper.playAudio(link)
    }

}