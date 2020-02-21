package com.ukdev.carcadasalborghetti.api

import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import okhttp3.ResponseBody
import retrofit2.http.Header
import retrofit2.http.POST

interface DownloadApi {

    @POST("2/files/download")
    suspend fun download(@Header("Dropbox-API-Arg") pathJson: MediaRequest): ResponseBody

}