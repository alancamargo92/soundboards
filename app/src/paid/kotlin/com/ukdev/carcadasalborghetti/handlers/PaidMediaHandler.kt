package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.ErrorType
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper
import com.ukdev.carcadasalborghetti.view.ViewLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class PaidMediaHandler(
        context: Context,
        callback: MediaCallback,
        view: ViewLayer,
        crashReportManager: CrashReportManager,
        private val apiProvider: ApiProvider
) : MediaHandler(context, callback, view, crashReportManager) {

    private val api by lazy { apiProvider.getDropboxService() }
    private val downloadApi by lazy { apiProvider.getDownloadService() }

    protected abstract fun playMedia(link: String, title: String)

    override suspend fun play(media: Media) {
        try {
            val link = getMediaLink(media.id)
            playMedia(link, media.title)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override suspend fun share(media: Media, mediaType: MediaType) {
        val request = MediaRequest(media.id)

        try {
            withContext(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    downloadApi.download(request).byteStream()
                }.use { byteStream ->
                    FileSharingHelper(context).shareFile(byteStream, media.title, mediaType)
                }
            }
        } catch (httpException: HttpException) {
            crashReportManager.logException(httpException)
            onError(ErrorType.UNKNOWN)
        } catch (ioException: IOException) {
            crashReportManager.logException(ioException)
            onError(ErrorType.CONNECTION)
        }
    }

    private suspend fun getMediaLink(mediaId: String): String {
        val request = MediaRequest(mediaId)
        return withContext(Dispatchers.IO) {
            api.getStreamLink(request).link
        }
    }

    private fun onError(errorType: ErrorType) {
        with(view) {
            notifyItemReady()
            onMediaError(errorType)
        }
    }

}