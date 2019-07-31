package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.activities.VideoActivity
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media

class VideoHandler(callback: MediaCallback) : PaidMediaHandler(callback) {

    override fun stop() {

    }

    override fun share(media: Media) {

    }

    override fun isPlaying() = false

    override fun onLinkReady(link: String) {
        val intent = VideoActivity.getIntent(context, link)
        context.startActivity(intent)
    }

    override fun onError() {

    }

}