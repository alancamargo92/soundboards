package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Audio

class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val txtTitle = itemView.findViewById<TextView>(R.id.txt_title)
    private val txtLength = itemView.findViewById<TextView>(R.id.txt_length)

    fun bindTo(audio: Audio) {
        txtTitle.text = itemView.context.getString(
                R.string.title_format, audio.position, audio.title
        )
        txtLength.text = audio.length
    }

}