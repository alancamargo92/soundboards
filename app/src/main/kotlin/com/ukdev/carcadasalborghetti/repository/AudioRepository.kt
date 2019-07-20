package com.ukdev.carcadasalborghetti.repository

import android.content.Context
import com.ukdev.carcadasalborghetti.model.Audio

abstract class AudioRepository(protected val context: Context) {
    abstract fun getAudios(): List<Audio>
}