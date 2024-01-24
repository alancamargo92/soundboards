package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ItemAudioBinding
import com.ukdev.carcadasalborghetti.domain.model.Media

class AudioViewHolder(private val binding: ItemAudioBinding) : MediaViewHolder(binding.root) {

    override fun bindTo(media: Media) {
        binding.txtTitle.text = itemView.context.getString(
                R.string.title_format, media.position, media.title
        ).removeSuffix(".mp3")
    }

}