package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar_media)
    private val icon: ImageView = itemView.findViewById(R.id.icon_media)

    abstract fun bindTo(media: Media)

    fun notifyItemClicked() {
        icon.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    fun notifyItemReady() {
        progressBar.visibility = GONE
        icon.visibility = VISIBLE
    }
}