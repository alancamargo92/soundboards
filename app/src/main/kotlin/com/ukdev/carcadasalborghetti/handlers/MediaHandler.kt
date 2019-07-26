package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaHandler(protected val callback: MediaCallback) {

    abstract fun play(media: Media)
    abstract fun stop()
    abstract fun share(media: Media)

}