package com.ukdev.carcadasalborghetti.framework.remote.api

import com.ukdev.carcadasalborghetti.framework.remote.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.framework.remote.api.responses.MediaListResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DropboxApi {

    @POST("2/files/list_folder")
    suspend fun listMedia(@Body body: MediaRequest): MediaListResponse

}