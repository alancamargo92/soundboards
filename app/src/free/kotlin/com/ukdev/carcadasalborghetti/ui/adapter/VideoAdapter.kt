package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.MediaViewHolder
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.VideoViewHolder

class VideoAdapter : MediaAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return VideoViewHolder(parent)
    }

}