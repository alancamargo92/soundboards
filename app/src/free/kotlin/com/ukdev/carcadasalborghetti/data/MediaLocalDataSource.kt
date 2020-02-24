package com.ukdev.carcadasalborghetti.data

import com.ukdev.carcadasalborghetti.model.Media

interface MediaLocalDataSource {
    suspend fun getMediaList(): List<Media>
}