package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ukdev.carcadasalborghetti.databinding.ItemOperationBinding
import com.ukdev.carcadasalborghetti.ui.adapter.diffcallback.OperationDiffCallback
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.OperationViewHolder
import com.ukdev.carcadasalborghetti.ui.model.UiOperation

class OperationAdapter(
    private val onItemClicked: (UiOperation) -> Unit
) : ListAdapter<UiOperation, OperationViewHolder>(OperationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOperationBinding.inflate(inflater, parent, false)
        return OperationViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        val operation = getItem(position)
        holder.bindTo(operation)
    }
}
