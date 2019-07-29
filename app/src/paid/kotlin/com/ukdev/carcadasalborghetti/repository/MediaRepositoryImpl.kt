package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.api.MediaRequest
import com.ukdev.carcadasalborghetti.model.Media
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MediaRepositoryImpl : MediaRepository() {

    private val api by lazy { DropboxApi.getService() }

    override fun getMedia(mediaType: Media.Type, resultCallback: ResultCallback) {
        val dir = if (mediaType == Media.Type.AUDIO)
            DropboxApi.DIR_AUDIO
        else
            DropboxApi.DIR_VIDEO
        val request = MediaRequest(dir)
        api.listMedia(request).enqueue(object : Callback<List<Media>> {
            override fun onResponse(call: Call<List<Media>>, response: Response<List<Media>>) {
                if (response.isSuccessful)
                    response.body()?.let(resultCallback::onMediaFound)
                else
                    resultCallback.onError()
            }

            override fun onFailure(call: Call<List<Media>>, t: Throwable) {
                resultCallback.onError()
            }
        })
    }

}