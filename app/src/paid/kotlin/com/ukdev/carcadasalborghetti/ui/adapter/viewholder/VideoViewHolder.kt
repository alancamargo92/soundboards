package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import androidx.core.view.isVisible
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ItemVideoBinding
import com.ukdev.carcadasalborghetti.domain.model.MediaV2

class VideoViewHolder(
    onItemClicked: (MediaV2) -> Unit,
    onItemLongClicked: (MediaV2) -> Unit,
    private val binding: ItemVideoBinding
) : MediaViewHolder(onItemClicked, onItemLongClicked, binding.root) {

    override fun bindTo(media: MediaV2) = with(binding) {
        txtTitle.text = itemView.context.getString(
            R.string.title_format,
            adapterPosition + 1,
            media.title
        ).removeSuffix(".mp4")

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

    private fun ItemVideoBinding.showLoading() {
        iconMedia.isVisible = false
        progressBarMedia.isVisible = true
    }

    private fun ItemVideoBinding.hideLoading() {
        iconMedia.isVisible = true
        progressBarMedia.isVisible = false
    }
}