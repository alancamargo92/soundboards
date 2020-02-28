package com.ukdev.carcadasalborghetti.handlers

import android.net.Uri
import com.ukdev.carcadasalborghetti.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.helpers.FileHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.Success
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

abstract class PaidMediaHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileHelper: FileHelper,
        private val remoteDataSource: MediaRemoteDataSource,
        private val ioHelper: IOHelper
) : MediaHandler(mediaHelper, crashReportManager, fileHelper) {

    protected abstract fun playMedia(link: Uri, title: String)

    override suspend fun play(media: Media) {
        val uriResult = ioHelper.safeIOCall(mainCall = {
            fileHelper.getFileUri(media.title)
        }, alternative = {
            val byteStream = remoteDataSource.download(media.id)
            fileHelper.getFileUri(byteStream, media)
        })

        if (uriResult is Success)
            playMedia(uriResult.body, media.title)
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