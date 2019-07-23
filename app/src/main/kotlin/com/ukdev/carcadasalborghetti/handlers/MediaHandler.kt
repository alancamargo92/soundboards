package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaHandler<T: Media>(protected val callback: MediaCallback) {

    abstract fun play(media: T)
    abstract fun stop()
    abstract fun share(media: T)

}