package com.ukdev.carcadasalborghetti.utils

import com.ukdev.carcadasalborghetti.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

private const val TIMEOUT = 20L

private val client = OkHttpClient.Builder().addInterceptor { chain ->
    val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
            .build()
    chain.proceed(newRequest)
}.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

fun <T: Any> getService(apiClass: KClass<T>, baseUrl: String): T {
    return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiClass.java)
}