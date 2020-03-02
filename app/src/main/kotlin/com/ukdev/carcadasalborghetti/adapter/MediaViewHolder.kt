package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.utils.hide
import com.ukdev.carcadasalborghetti.utils.show

abstract class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar_media)
    private val icon: ImageView = itemView.findViewById(R.id.icon_media)

    abstract fun bindTo(media: Media)

    fun notifyItemClicked() {
        icon.hide()
        progressBar.show()
    }

    fun notifyItemReady() {
        progressBar.hide()
        icon.show()
    }

}