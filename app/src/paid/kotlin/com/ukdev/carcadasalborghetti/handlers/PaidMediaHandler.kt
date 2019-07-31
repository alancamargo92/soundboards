package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.responses.StreamLinkResponse
import com.ukdev.carcadasalborghetti.listeners.LinkCallback
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class PaidMediaHandler(callback: MediaCallback) : MediaHandler(callback), LinkCallback {

    private val api = DropboxApi.getService()

    override fun play(media: Media) {
        getMediaLink(media)
    }

    private fun getMediaLink(media: Media) {
        val request = MediaRequest(media.path)
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