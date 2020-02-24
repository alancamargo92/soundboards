package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.helpers.FileSharingHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class VideoHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileSharingHelper,
        remoteDataSource: MediaRemoteDataSource
) : PaidMediaHandler(mediaHelper, crashReportManager, fileSharingHelper, remoteDataSource) {

    override fun playMedia(link: String, title: String) {
        mediaHelper.playVideo(link, title)
    }

}