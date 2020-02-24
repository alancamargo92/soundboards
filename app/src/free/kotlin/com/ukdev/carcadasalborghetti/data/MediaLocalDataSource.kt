package com.ukdev.carcadasalborghetti.data

import android.net.Uri

interface MediaLocalDataSource {
    fun getTitles(): Array<String>
    suspend fun getAudioUris(): Array<Uri>
}