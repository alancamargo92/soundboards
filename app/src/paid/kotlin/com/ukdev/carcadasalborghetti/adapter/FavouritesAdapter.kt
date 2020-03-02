package com.ukdev.carcadasalborghetti.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.MediaType

class FavouritesAdapter : MediaAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_AUDIO) {
            val itemView = inflater.inflate(R.layout.item_audio, parent, false)
            AudioViewHolder(itemView)
        } else {
            val itemView = inflater.inflate(R.layout.item_video, parent, false)
            VideoViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val media = data?.get(position)
        media?.let {
            return when (it.type) {
                MediaType.AUDIO -> VIEW_TYPE_AUDIO
                MediaType.VIDEO -> VIEW_TYPE_VIDEO
                else -> throw IllegalArgumentException("Must be either audio or video")
            }
        } ?: throw IllegalStateException("Data has not been submitted to adapter")
    }

    private companion object {
        const val VIEW_TYPE_AUDIO = 0
        const val VIEW_TYPE_VIDEO = 1
    }

}