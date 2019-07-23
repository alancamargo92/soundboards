package com.ukdev.carcadasalborghetti.utils

import com.ukdev.carcadasalborghetti.model.Media

interface MediaHandler<T: Media> {

    fun play(media: T)
    fun stop()
    fun share(media: T)

}