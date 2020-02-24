package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper

abstract class PaidMediaHandler(
        context: Context,
        crashReportManager: CrashReportManager,
        fileSharingHelper: FileSharingHelper,
        private val remoteDataSource: MediaRemoteDataSource
) : MediaHandler(context, crashReportManager, fileSharingHelper) {

    protected abstract fun playMedia(link: String, title: String)

    override suspend fun play(media: Media) {
        try {
            val link = remoteDataSource.getStreamLink(media.id)
            playMedia(link, media.title)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override suspend fun share(media: Media, mediaType: MediaType) {
        try {
            val byteStream = remoteDataSource.download(media.id)
            fileSharingHelper.shareFile(byteStream, media.title, mediaType)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

}