package com.ukdev.carcadasalborghetti.data.local

import com.ukdev.carcadasalborghetti.domain.model.Media

interface MediaLocalDataSource {
    suspend fun getMediaList(): List<Media>
}