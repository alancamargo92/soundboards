package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ItemVideoBinding
import com.ukdev.carcadasalborghetti.domain.entities.Media

class VideoViewHolder(private val binding: ItemVideoBinding) : MediaViewHolder(binding.root) {

    override fun bindTo(media: Media) {
        binding.txtTitle.text = itemView.context.getString(
            R.string.title_format, media.position, media.title
        ).removeSuffix(".mp4")
    }
}