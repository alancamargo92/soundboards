package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import androidx.core.view.isVisible
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ItemAudioBinding
import com.ukdev.carcadasalborghetti.domain.model.MediaV2

class AudioViewHolder(
    onItemClicked: (MediaV2) -> Unit,
    onItemLongClicked: (MediaV2) -> Unit,
    private val binding: ItemAudioBinding
) : MediaViewHolder(onItemClicked, onItemLongClicked, binding.root) {

    override fun bindTo(media: MediaV2) = with(binding) {
        txtTitle.text = itemView.context.getString(
                R.string.title_format, adapterPosition, media.title
        ).removeSuffix(suffix = ".mp3")

        root.setOnClickListener {
            showLoading()
            onItemClicked(media)
            hideLoading()
        }

        root.setOnLongClickListener {
            showLoading()
            onItemLongClicked(media)
            hideLoading()
            true
        }
    }

    private fun ItemAudioBinding.showLoading() {
        iconMedia.isVisible = false
        progressBarMedia.isVisible = true
    }

    private fun ItemAudioBinding.hideLoading() {
        iconMedia.isVisible = true
        progressBarMedia.isVisible = false
    }
}
