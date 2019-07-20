package com.ukdev.carcadasalborghetti.repository

import android.content.Context
import com.ukdev.carcadasalborghetti.model.Audio

class AudioRepositoryImpl(context: Context) : AudioRepository(context) {

    override fun getAudios(): List<Audio> {
        // TODO
        return emptyList()
    }

}