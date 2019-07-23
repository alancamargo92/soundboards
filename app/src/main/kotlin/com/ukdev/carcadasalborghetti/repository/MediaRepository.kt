package com.ukdev.carcadasalborghetti.repository

import android.content.Context
import com.ukdev.carcadasalborghetti.model.Media
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class MediaRepository<T: Media> : KoinComponent {

    protected val context: Context by inject()

    abstract fun getMedia(): List<T>

}