package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.databinding.ItemOperationBinding
import com.ukdev.carcadasalborghetti.ui.model.UiOperation

class OperationViewHolder(
    private val binding: ItemOperationBinding,
    private val onItemClicked: (UiOperation) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(operation: UiOperation) = with(operation) {
        binding.imgOperation.setImageResource(icon)
        binding.txtOperation.setText(text)
        binding.root.setOnClickListener { onItemClicked(operation) }
    }
}
