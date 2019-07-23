package com.ukdev.carcadasalborghetti.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Audio

class AudioAdapter : MediaAdapter<Audio>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder<Audio> {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_audio, parent, false)
        return AudioViewHolder(itemView)
    }

}