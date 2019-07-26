package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.model.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoRepository : MediaRepository<Video>() {

    private val api by lazy { DropboxApi.getService() }

    override fun getMedia(resultCallback: ResultCallback<Video>) {
        api.getVideos().enqueue(object : Callback<List<Video>> {
            override fun onResponse(call: Call<List<Video>>, response: Response<List<Video>>) {
                if (response.isSuccessful)
                    response.body()?.let(resultCallback::onMediaFound)
                else
                    resultCallback.onError()
            }

            override fun onFailure(call: Call<List<Video>>, t: Throwable) {
                resultCallback.onError()
            }
        })
    }

}