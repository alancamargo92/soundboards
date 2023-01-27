package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.databinding.ItemAudioBinding
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.AudioViewHolder
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.MediaViewHolder

class AudioAdapter : MediaAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAudioBinding.inflate(inflater, parent, false)
        return AudioViewHolder(binding)
    }

}