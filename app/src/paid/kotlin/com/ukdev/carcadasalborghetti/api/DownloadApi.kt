package com.ukdev.carcadasalborghetti.api

import com.google.gson.GsonBuilder
import com.ukdev.carcadasalborghetti.BuildConfig
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST

interface DownloadApi {

    @POST("2/files/download")
    fun download(@Header("Dropbox-API-Arg") pathJson: MediaRequest): Call<ResponseBody>

    companion object {
        private val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
                    .build()
            chain.proceed(newRequest)
        }.build()

        fun getService(): DownloadApi {
            val gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                    .client(client)
                    .baseUrl(BuildConfig.BASE_URL_DOWNLOADS)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(DownloadApi::class.java)
        }
    }

}