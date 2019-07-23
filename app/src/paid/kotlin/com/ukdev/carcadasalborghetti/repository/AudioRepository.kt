package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.model.Audio

class AudioRepository : MediaRepository<Audio>() {

    override fun getMedia(): List<Audio> {
        // TODO
        return emptyList()
    }

}