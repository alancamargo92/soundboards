package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.AudioViewHolder
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.MediaViewHolder

class AudioAdapter : MediaAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_audio, parent, false)
        return AudioViewHolder(itemView)
    }

}