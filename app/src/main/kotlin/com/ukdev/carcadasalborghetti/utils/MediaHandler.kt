package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaHandler<T: Media>(
        protected val context: Context,
        protected val callback: MediaCallback
) {

    abstract fun play(media: T)
    abstract fun stop()
    abstract fun share(media: T)

}