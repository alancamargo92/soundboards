package com.ukdev.carcadasalborghetti.ui.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.ukdev.carcadasalborghetti.ui.model.UiOperation

class OperationDiffCallback : DiffUtil.ItemCallback<UiOperation>() {

    override fun areItemsTheSame(oldItem: UiOperation, newItem: UiOperation): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UiOperation, newItem: UiOperation): Boolean {
        return oldItem == newItem
    }
}
