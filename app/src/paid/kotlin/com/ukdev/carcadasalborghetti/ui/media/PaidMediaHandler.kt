package com.ukdev.carcadasalborghetti.ui.media

import android.net.Uri
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.data.tools.PaidFileHelper
import com.ukdev.carcadasalborghetti.domain.model.Media

abstract class PaidMediaHandler(
    mediaHelper: MediaHelper,
    logger: Logger,
    private val remoteDataSource: MediaRemoteDataSource,
    private val paidFileHelper: PaidFileHelper
) : MediaHandler(mediaHelper, logger, paidFileHelper) {

    protected abstract fun playMedia(link: Uri, media: Media)

    override suspend fun play(media: Media) {
        val fileUri = runCatching {
            fileHelper.getFileUri(media.title)
        }.getOrElse {
            val file = remoteDataSource.download(media.id)
            paidFileHelper.getFileUri(file.name)
        }

        playMedia(fileUri, media)
    }

    override suspend fun share(media: Media) {
        runCatching {
            val uri = fileHelper.getFileUri(media.title)
            fileHelper.shareFile(uri, media)
        }.getOrElse {
            val downloader = remoteDataSource.download(media.id)
            paidFileHelper.shareFile(downloader, media)
        }
    }
}
