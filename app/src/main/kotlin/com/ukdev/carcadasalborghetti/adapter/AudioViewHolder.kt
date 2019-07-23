package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import android.widget.TextView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Audio

class AudioViewHolder(itemView: View) : MediaViewHolder<Audio>(itemView) {

    private val txtTitle = itemView.findViewById<TextView>(R.id.txt_title)
    private val txtLength = itemView.findViewById<TextView>(R.id.txt_length)

    override fun bindTo(media: Audio) {
        txtTitle.text = itemView.context.getString(
                R.string.title_format, media.position, media.title
        )
        txtLength.text = media.length
    }

}