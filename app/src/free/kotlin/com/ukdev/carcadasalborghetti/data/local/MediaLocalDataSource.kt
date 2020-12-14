package com.ukdev.carcadasalborghetti.data.local

import com.ukdev.carcadasalborghetti.domain.entities.Media

interface MediaLocalDataSource {
    suspend fun getMediaList(): List<Media>
}