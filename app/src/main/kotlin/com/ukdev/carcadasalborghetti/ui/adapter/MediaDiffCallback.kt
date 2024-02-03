package com.ukdev.carcadasalborghetti.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ukdev.carcadasalborghetti.domain.model.MediaV2

class MediaDiffCallback : DiffUtil.ItemCallback<MediaV2>() {

    override fun areItemsTheSame(oldItem: MediaV2, newItem: MediaV2): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaV2, newItem: MediaV2): Boolean {
        return oldItem == newItem
    }
}
