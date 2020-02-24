package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import com.ukdev.carcadasalborghetti.activities.VideoActivity
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper

class VideoHandler(
        context: Context,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileSharingHelper,
        remoteDataSource: MediaRemoteDataSource
) : PaidMediaHandler(context, crashReportManager, fileSharingHelper, remoteDataSource) {

    override fun stop() { }

    override fun isPlaying() = isPlayingLiveData.apply {
        value = false
    }

    override fun playMedia(link: String, title: String) {
        val intent = VideoActivity.getIntent(context, title, link)
        context.startActivity(intent)
    }

}