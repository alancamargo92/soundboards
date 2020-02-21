package com.ukdev.carcadasalborghetti.api

import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.responses.MediaResponse
import com.ukdev.carcadasalborghetti.api.responses.StreamLinkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DropboxApi {

    @POST("2/files/list_folder")
    suspend fun listMedia(@Body body: MediaRequest): MediaResponse

    @POST("2/files/get_temporary_link")
    suspend fun getStreamLink(@Body body: MediaRequest): StreamLinkResponse

    companion object {
        const val DIR_AUDIO = "/audios"
        const val DIR_VIDEO = "/videos"
    }

}