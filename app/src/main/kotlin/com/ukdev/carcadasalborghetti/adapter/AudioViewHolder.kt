package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Media
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_audio.*

class AudioViewHolder(itemView: View) : MediaViewHolder(itemView), LayoutContainer {

    override val containerView: View? = itemView

    override fun bindTo(media: Media) {
        txt_title.text = itemView.context.getString(
                R.string.title_format, media.position, media.title
        ).removeSuffix(".mp3")
    }

}