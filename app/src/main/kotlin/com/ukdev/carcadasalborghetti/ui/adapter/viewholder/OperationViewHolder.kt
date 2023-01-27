package com.ukdev.carcadasalborghetti.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.databinding.ItemOperationBinding
import com.ukdev.carcadasalborghetti.domain.entities.Operation

class OperationViewHolder(
    private val binding: ItemOperationBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(operation: Operation) = with(operation) {
        binding.imgOperation.setImageResource(icon)
        binding.txtOperation.setText(text)
    }
}
