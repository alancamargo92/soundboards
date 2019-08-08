package com.ukdev.carcadasalborghetti.adapter

import android.view.ViewGroup

class VideoAdapter : MediaAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return VideoViewHolder(parent)
    }

}