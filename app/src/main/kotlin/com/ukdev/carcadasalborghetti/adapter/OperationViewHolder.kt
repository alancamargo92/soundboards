package com.ukdev.carcadasalborghetti.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.model.Operation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_operation.*

class OperationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View? = itemView

    fun bindTo(operation: Operation) = with(operation) {
        img_operation.setImageResource(icon)
        txt_operation.setText(text)
    }

}