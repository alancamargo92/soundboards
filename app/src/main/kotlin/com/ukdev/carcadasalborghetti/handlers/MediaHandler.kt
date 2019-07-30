package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class MediaHandler(protected val callback: MediaCallback) : KoinComponent {

    protected val context by inject<Context>()

    abstract fun play(media: Media)
    abstract fun stop()
    abstract fun share(media: Media)

}