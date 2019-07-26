package com.ukdev.carcadasalborghetti.api

import com.ukdev.carcadasalborghetti.BuildConfig.ACCESS_TOKEN
import com.ukdev.carcadasalborghetti.BuildConfig.BASE_URL
import com.ukdev.carcadasalborghetti.model.Audio
import com.ukdev.carcadasalborghetti.model.Video
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface DropboxApi {

    @POST("2/files/list_folder")
    fun getAudios(@Body dir: String = "/audios"): Call<List<Audio>>

    @POST("2/files/list_folder")
    fun getVideos(@Body dir: String = "/videos"): Call<List<Video>>

    companion object {
        private val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $ACCESS_TOKEN")
                    .build()
            chain.proceed(newRequest)
        }.build()

        fun getService(): DropboxApi {
            return Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DropboxApi::class.java)
        }
    }

}