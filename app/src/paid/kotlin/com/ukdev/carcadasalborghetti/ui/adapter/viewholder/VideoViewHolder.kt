package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.domain.entities.Media

class VideoViewHolder(itemView: View) : MediaViewHolder(itemView) {

    private val txtTitle = itemView.findViewById<TextView>(R.id.txt_title)

    override fun bindTo(media: Media) {
        txtTitle.text = itemView.context.getString(
                R.string.title_format, media.position, media.title
        ).removeSuffix(".mp4")
    }

}