package com.ukdev.carcadasalborghetti.handlers

import android.media.MediaPlayer
import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.responses.StreamLinkResponse
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AudioHandler(callback: MediaCallback) : MediaHandler(callback) {

    private val api = DropboxApi.getService()

    private var mediaPlayer: MediaPlayer? = null

    override fun play(media: Media) {
        val request = MediaRequest(media.path)
        api.getStreamLink(request).enqueue(object : Callback<StreamLinkResponse> {
            override fun onResponse(
                    call: Call<StreamLinkResponse>,
                    response: Response<StreamLinkResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { linkResponse ->
                        initialiseMediaPlayer(linkResponse.link)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<StreamLinkResponse>, t: Throwable) {

            }
        })
    }

    override fun stop() {

    }

    override fun share(media: Media) {

    }

    private fun initialiseMediaPlayer(mediaLink: String) {
        mediaPlayer?.run {
            release()
            setOnPreparedListener { mp ->
                mp.start()
            }
            setOnErrorListener { mp, _, _ ->
                mp.reset()
                false
            }

            setDataSource(mediaLink)
            prepareAsync()
        }
    }

}