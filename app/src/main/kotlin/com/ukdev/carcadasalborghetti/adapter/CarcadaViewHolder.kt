package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Carcada

class CarcadaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val txtTitle = itemView.findViewById<TextView>(R.id.txt_title)
    private val txtLength = itemView.findViewById<TextView>(R.id.txt_length)

    fun bindTo(carcada: Carcada) {
        txtTitle.text = itemView.context.getString(
                R.string.title_format, carcada.position, carcada.title
        )
        txtLength.text = carcada.length
    }

}