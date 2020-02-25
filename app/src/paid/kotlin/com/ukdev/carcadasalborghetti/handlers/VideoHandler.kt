package com.ukdev.carcadasalborghetti.handlers

import android.net.Uri
import com.ukdev.carcadasalborghetti.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.helpers.FileHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class VideoHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileHelper: FileHelper,
        remoteDataSource: MediaRemoteDataSource,
        ioHelper: IOHelper
) : PaidMediaHandler(mediaHelper, crashReportManager, fileHelper, remoteDataSource, ioHelper) {

    override fun playMedia(link: Uri, title: String) {
        mediaHelper.playVideo(link, title)
    }

}