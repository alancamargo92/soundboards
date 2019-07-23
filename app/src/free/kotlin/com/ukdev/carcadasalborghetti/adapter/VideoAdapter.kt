package com.ukdev.carcadasalborghetti.adapter

import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.model.Video

class VideoAdapter : MediaAdapter<Video>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder<Video> {
        return VideoViewHolder(parent)
    }

}