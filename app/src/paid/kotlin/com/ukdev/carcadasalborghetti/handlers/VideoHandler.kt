package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.activities.VideoActivity
import com.ukdev.carcadasalborghetti.listeners.MediaCallback

class VideoHandler(callback: MediaCallback) : PaidMediaHandler(callback) {

    override fun stop() { }

    override fun isPlaying() = false

    override fun onLinkReady(link: String, title: String) {
        val intent = VideoActivity.getIntent(context, title, link)
        context.startActivity(intent)
    }

}