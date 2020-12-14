package com.ukdev.carcadasalborghetti.adapter

import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.MediaViewHolder

class VideoAdapter : MediaAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return VideoViewHolder(parent)
    }

}