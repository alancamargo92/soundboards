package com.ukdev.carcadasalborghetti.framework.remote.api

import com.ukdev.carcadasalborghetti.framework.remote.api.requests.MediaRequest
import okhttp3.ResponseBody
import retrofit2.http.Header
import retrofit2.http.POST

interface DownloadApi {

    @POST("2/files/download")
    suspend fun download(@Header("Dropbox-API-Arg") mediaRequest: MediaRequest): ResponseBody

}