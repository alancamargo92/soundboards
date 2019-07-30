package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.responses.MediaResponse
import com.ukdev.carcadasalborghetti.model.MediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MediaRepositoryImpl : MediaRepository() {

    private val api by lazy { DropboxApi.getService() }

    override fun getMedia(mediaType: MediaType, resultCallback: ResultCallback) {
        val dir = if (mediaType == MediaType.AUDIO)
            DropboxApi.DIR_AUDIO
        else
            DropboxApi.DIR_VIDEO
        val request = MediaRequest(dir)
        api.listMedia(request).enqueue(object : Callback<MediaResponse> {
            override fun onResponse(call: Call<MediaResponse>, response: Response<MediaResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        resultCallback.onMediaFound(it.entries)
                    }
                } else {
                    resultCallback.onError()
                }
            }

            override fun onFailure(call: Call<MediaResponse>, t: Throwable) {
                resultCallback.onError()
            }
        })
    }

}