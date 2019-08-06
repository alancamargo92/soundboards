package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType

class VideoHandler(callback: MediaCallback) : MediaHandler(callback) {
    override fun play(media: Media) { }
    override fun stop() { }
    override fun share(media: Media, mediaType: MediaType) { }
    override fun isPlaying() = false
}