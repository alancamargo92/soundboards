package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.model.Audio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AudioRepository : MediaRepository<Audio>() {

    private val api by lazy { DropboxApi.getService() }

    override fun getMedia(resultCallback: ResultCallback<Audio>) {
        api.getAudios().enqueue(object : Callback<List<Audio>> {
            override fun onResponse(call: Call<List<Audio>>, response: Response<List<Audio>>) {
                if (response.isSuccessful)
                    response.body()?.let(resultCallback::onMediaFound)
                else
                    resultCallback.onError()
            }

            override fun onFailure(call: Call<List<Audio>>, t: Throwable) {
                resultCallback.onError()
            }
        })
    }

}