package com.ukdev.carcadasalborghetti.ui.media

import android.net.Uri
import com.ukdev.carcadasalborghetti.data.model.Success
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.data.tools.IOHelper
import com.ukdev.carcadasalborghetti.data.tools.PaidFileHelper

abstract class PaidMediaHandler(
    mediaHelper: MediaHelper,
    logger: Logger,
    private val remoteDataSource: MediaRemoteDataSource,
    private val ioHelper: IOHelper,
    private val paidFileHelper: PaidFileHelper
) : MediaHandler(mediaHelper, logger, paidFileHelper) {

    protected abstract fun playMedia(link: Uri, media: Media)

    override suspend fun play(media: Media) {
        val uriResult = ioHelper.safeIOCall(mainCall = {
            fileHelper.getFileUri(media.title)
        }, alternative = {
            val file = remoteDataSource.download(media.id)
            paidFileHelper.getFileUri(file.name)
        })

        if (uriResult is Success)
            playMedia(uriResult.body, media)
    }

    override suspend fun share(media: Media) {
        ioHelper.safeIOCall(mainCall = {
            val uri = fileHelper.getFileUri(media.title)
            fileHelper.shareFile(uri, media)
        }, alternative = {
            val downloader = remoteDataSource.download(media.id)
            paidFileHelper.shareFile(downloader, media)
        })
    }

}