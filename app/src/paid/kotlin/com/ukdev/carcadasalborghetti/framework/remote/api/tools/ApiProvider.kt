package com.ukdev.carcadasalborghetti.framework.remote.api.tools

import com.ukdev.carcadasalborghetti.framework.remote.api.DownloadApi
import com.ukdev.carcadasalborghetti.framework.remote.api.DropboxApi
import com.ukdev.carcadasalborghetti.framework.remote.api.TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class ApiProvider(
        private val dropboxBaseUrl: String,
        private val downloadBaseUrl: String,
        private val tokenHelper: TokenHelper
) {

    fun getDropboxService(): DropboxApi {
        return getService(DropboxApi::class, dropboxBaseUrl)
    }

    fun getDownloadService(): DownloadApi {
        return getService(DownloadApi::class, downloadBaseUrl)
    }

    private fun <T: Any> getService(apiClass: KClass<T>, baseUrl: String): T {
        return Retrofit.Builder()
                .client(buildClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(apiClass.java)
    }

    private fun buildClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${tokenHelper.getAccessToken()}")
                    .build()
            chain.proceed(newRequest)
        }.readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

}