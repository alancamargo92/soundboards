package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ItemMediaBinding
import com.ukdev.carcadasalborghetti.ui.model.UiMedia

class MediaViewHolder(
    private val onItemClicked: (UiMedia) -> Unit,
    private val onItemLongClicked: (UiMedia) -> Unit,
    private val binding: ItemMediaBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(media: UiMedia) = with(binding) {
        txtTitle.text = itemView.context.getString(
            R.string.title_format,
            adapterPosition + 1,
            media.title
        ).replace("[.mp3|4]".toRegex(), "")
        iconMedia.setImageResource(media.type.iconRes)

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

    private fun ItemMediaBinding.showLoading() {
        iconMedia.isVisible = false
        progressBarMedia.isVisible = true
    }

    private fun ItemMediaBinding.hideLoading() {
        iconMedia.isVisible = true
        progressBarMedia.isVisible = false
    }
}
