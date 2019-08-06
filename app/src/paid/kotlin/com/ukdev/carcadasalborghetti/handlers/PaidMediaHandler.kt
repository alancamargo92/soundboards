package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.api.DownloadApi
import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.responses.StreamLinkResponse
import com.ukdev.carcadasalborghetti.listeners.LinkCallback
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.FileUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class PaidMediaHandler(callback: MediaCallback) : MediaHandler(callback), LinkCallback {

    private val api = DropboxApi.getService()
    private val downloadApi = DownloadApi.getService()

    override fun play(media: Media) {
        getMediaLink(media)
    }

    override fun share(media: Media) {
        val request = MediaRequest(media.id)
        downloadApi.download(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.byteStream()?.let {
                        FileUtils(context).run {
                            val file = getFile(it, media.title)
                            val uri = getUri(file)
                            shareFile(uri, MediaType.AUDIO)
                        }
                    }
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError()
            }
        })
    }

    private fun getMediaLink(media: Media) {
        val request = MediaRequest(media.id)
        api.getStreamLink(request).enqueue(object : Callback<StreamLinkResponse> {
            override fun onResponse(
                    call: Call<StreamLinkResponse>,
                    response: Response<StreamLinkResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { linkResponse ->
                        onLinkReady(linkResponse.link)
                    }
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<StreamLinkResponse>, t: Throwable) {
                onError()
            }
        })
    }

}