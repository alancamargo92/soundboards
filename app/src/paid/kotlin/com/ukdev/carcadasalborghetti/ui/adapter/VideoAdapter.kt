package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.databinding.ItemVideoBinding
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.MediaViewHolder
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.VideoViewHolder

class VideoAdapter : MediaAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(inflater, parent, false)
        return VideoViewHolder(binding)
    }

}
