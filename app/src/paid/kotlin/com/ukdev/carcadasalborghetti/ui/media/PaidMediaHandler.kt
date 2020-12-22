package com.ukdev.carcadasalborghetti.ui.media

import android.net.Uri
import com.ukdev.carcadasalborghetti.data.entities.Success
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper

abstract class PaidMediaHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileHelper: FileHelper,
        private val remoteDataSource: MediaRemoteDataSource,
        private val ioHelper: IOHelper
) : MediaHandler(mediaHelper, crashReportManager, fileHelper) {

    protected abstract fun playMedia(link: Uri, media: Media)

    override suspend fun play(media: Media) {
        val uriResult = ioHelper.safeIOCall(mainCall = {
            fileHelper.getFileUri(media.title)
        }, alternative = {
            val byteStream = remoteDataSource.download(media.id)
            fileHelper.getFileUri(byteStream, media)
        })

        if (uriResult is Success)
            playMedia(uriResult.body, media)
    }

    override suspend fun share(media: Media) {
        ioHelper.safeIOCall(mainCall = {
            val uri = fileHelper.getFileUri(media.title)
            fileHelper.shareFile(uri, media)
        }, alternative = {
            val byteStream = remoteDataSource.download(media.id)
            fileHelper.shareFile(byteStream, media)
        })
    }

}