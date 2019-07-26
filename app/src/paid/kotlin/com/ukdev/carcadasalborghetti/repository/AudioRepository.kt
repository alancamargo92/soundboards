package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.model.Audio

class AudioRepository : MediaRepository<Audio>() {

    private val api by lazy { DropboxApi.getService() }

    override fun getMedia(): List<Audio> {
        // TODO: make async
        return emptyList()
    }

}