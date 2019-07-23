package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaViewHolder<T: Media>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindTo(media: T)

}