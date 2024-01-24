package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.databinding.ItemAudioBinding
import com.ukdev.carcadasalborghetti.databinding.ItemVideoBinding
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.AudioViewHolder
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.MediaViewHolder
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.VideoViewHolder

class FavouritesAdapter : MediaAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_AUDIO) {
            val binding = ItemAudioBinding.inflate(inflater, parent, false)
            AudioViewHolder(binding)
        } else {
            val binding = ItemVideoBinding.inflate(inflater, parent, false)
            VideoViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val media = data?.get(position)
        media?.let {
            return when (it.type) {
                MediaType.AUDIO -> VIEW_TYPE_AUDIO
                MediaType.VIDEO -> VIEW_TYPE_VIDEO
                else -> error("Must be either audio or video")
            }
        } ?: error("Data has not been submitted to adapter")
    }

    private companion object {
        const val VIEW_TYPE_AUDIO = 0
        const val VIEW_TYPE_VIDEO = 1
    }

}