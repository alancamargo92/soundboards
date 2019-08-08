package com.ukdev.carcadasalborghetti.api

import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.responses.MediaResponse
import com.ukdev.carcadasalborghetti.api.responses.StreamLinkResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DropboxApi {

    @POST("2/files/list_folder")
    fun listMedia(@Body body: MediaRequest): Call<MediaResponse>

    @POST("2/files/get_temporary_link")
    fun getStreamLink(@Body body: MediaRequest): Call<StreamLinkResponse>

    companion object {
        const val DIR_AUDIO = "/audios"
        const val DIR_VIDEO = "/videos"
    }

}