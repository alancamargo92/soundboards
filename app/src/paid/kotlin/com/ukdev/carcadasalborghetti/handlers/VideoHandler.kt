package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.activities.VideoActivity
import com.ukdev.carcadasalborghetti.listeners.MediaCallback

class VideoHandler(callback: MediaCallback) : PaidMediaHandler(callback) {

    override fun stop() {

    }

    override fun isPlaying() = false

    override fun onLinkReady(link: String) {
        val intent = VideoActivity.getIntent(context, link)
        context.startActivity(intent)
    }

    override fun onError() {

    }

}