package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import com.ukdev.carcadasalborghetti.activities.VideoActivity
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class VideoHandler(
        context: Context,
        crashReportManager: CrashReportManager,
        apiProvider: ApiProvider
) : PaidMediaHandler(context, crashReportManager, apiProvider) {

    override fun stop() { }

    override fun isPlaying() = isPlayingLiveData.apply {
        value = false
    }

    override fun playMedia(link: String, title: String) {
        val intent = VideoActivity.getIntent(context, title, link)
        context.startActivity(intent)
    }

}