package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import com.ukdev.carcadasalborghetti.databinding.ItemVideoBinding
import com.ukdev.carcadasalborghetti.domain.model.MediaV2

class VideoViewHolder(
    onItemClicked: (MediaV2) -> Unit,
    onItemLongClicked: (MediaV2) -> Unit,
    binding: ItemVideoBinding
) : MediaViewHolder(onItemClicked, onItemLongClicked, binding.root) {

    override fun bindTo(media: MediaV2) = Unit
}
