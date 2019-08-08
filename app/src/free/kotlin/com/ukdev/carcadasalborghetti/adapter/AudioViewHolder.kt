package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import android.widget.TextView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Media

class AudioViewHolder(itemView: View) : MediaViewHolder(itemView) {

    private val txtTitle = itemView.findViewById<TextView>(R.id.txt_title)

    override fun bindTo(media: Media) {
        txtTitle.text = itemView.context.getString(
                R.string.title_format, media.position, media.title
        )
    }

}