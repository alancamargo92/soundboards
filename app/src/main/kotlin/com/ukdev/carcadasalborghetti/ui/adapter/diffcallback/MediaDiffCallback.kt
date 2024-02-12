package com.ukdev.carcadasalborghetti.ui.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.ukdev.carcadasalborghetti.ui.model.UiMedia

class MediaDiffCallback : DiffUtil.ItemCallback<UiMedia>() {

    override fun areItemsTheSame(oldItem: UiMedia, newItem: UiMedia): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: UiMedia, newItem: UiMedia): Boolean {
        return oldItem == newItem
    }
}
