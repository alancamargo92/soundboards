package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Carcada
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_carcada.*

class CarcadaViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

    fun bindTo(carcada: Carcada) {
        txt_title.text = containerView.context.getString(
                R.string.title_format, carcada.position, carcada.title
        )
        txt_length.text = carcada.length
    }

}