package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Video

class VideoHandler(callback: MediaCallback) : MediaHandler<Video>(callback) {

    override fun play(media: Video) { }

    override fun stop() { }

    override fun share(media: Video) { }

}