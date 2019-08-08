package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.activities.VideoActivity
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.view.ViewLayer

class VideoHandler(callback: MediaCallback, view: ViewLayer) : PaidMediaHandler(callback, view) {

    override fun stop() { }

    override fun isPlaying() = false

    override fun onLinkReady(link: String, title: String) {
        val intent = VideoActivity.getIntent(context, title, link)
        context.startActivity(intent)
    }

}