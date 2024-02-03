package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.domain.model.MediaV2

abstract class MediaViewHolder(
    protected val onItemClicked: (MediaV2) -> Unit,
    protected val onItemLongClicked: (MediaV2) -> Unit,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar_media)
    private val icon: ImageView = itemView.findViewById(R.id.icon_media)

    abstract fun bindTo(media: MediaV2)

    fun notifyItemClicked() {
        icon.isVisible = false
        progressBar.isVisible = true
    }

    fun notifyItemReady() {
        progressBar.isVisible = false
        icon.isVisible = true
    }
}
