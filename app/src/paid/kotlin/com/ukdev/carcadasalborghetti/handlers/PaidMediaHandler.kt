package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.ukdev.carcadasalborghetti.BuildConfig
import com.ukdev.carcadasalborghetti.api.DownloadApi
import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.listeners.LinkCallback
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.ErrorType
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.FileUtils
import com.ukdev.carcadasalborghetti.utils.getService
import com.ukdev.carcadasalborghetti.view.ViewLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class PaidMediaHandler(
        context: Context,
        callback: MediaCallback,
        view: ViewLayer,
        crashReportManager: CrashReportManager
) : MediaHandler(context, callback, view, crashReportManager), LinkCallback {

    private val api by lazy { getService(DropboxApi::class, BuildConfig.BASE_URL) }
    private val downloadApi by lazy { getService(DownloadApi::class, BuildConfig.BASE_URL_DOWNLOADS) }

    protected abstract fun playMedia(link: String, title: String)

    override suspend fun play(media: Media) {
        try {
            val link = getMediaLink(media.id)
            playMedia(link, media.title)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
        }
    }

    override fun share(media: Media, mediaType: MediaType) {
        val request = MediaRequest(media.id)
        downloadApi.download(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    view.notifyItemReady()
                    response.body()?.byteStream()?.let {
                        FileUtils(context).run {
                            val file = getFile(it, media.title)
                            val uri = getUri(file)
                            shareFile(uri, mediaType)
                        }
                    }
                } else {
                    Crashlytics.log("Error playing")
                    onError(ErrorType.UNKNOWN)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(ErrorType.CONNECTION)
            }
        })
    }

    override fun onError(errorType: ErrorType) {
        view.notifyItemReady()
        view.onMediaError(errorType)
    }

    private suspend fun getMediaLink(mediaId: String) = withContext(Dispatchers.IO) {
        val request = MediaRequest(mediaId)
        api.getStreamLink(request).link
    }

}