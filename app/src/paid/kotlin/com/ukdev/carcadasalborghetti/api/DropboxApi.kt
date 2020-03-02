package com.ukdev.carcadasalborghetti.api

import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.responses.MediaListResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DropboxApi {

    @POST("2/files/list_folder")
    suspend fun listMedia(@Body body: MediaRequest): MediaListResponse

}