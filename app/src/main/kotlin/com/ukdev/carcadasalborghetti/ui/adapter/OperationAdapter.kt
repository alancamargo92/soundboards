package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.databinding.ItemOperationBinding
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.OperationViewHolder

class OperationAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<OperationViewHolder>() {

    private var data: List<Operation> = emptyList()

    fun submitData(data: List<Operation>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOperationBinding.inflate(inflater, parent, false)
        return OperationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        val operation = data[position]
        with(holder) {
            bindTo(operation)
            itemView.setOnClickListener {
                listener.onItemClick(operation)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    interface ItemClickListener {
        fun onItemClick(operation: Operation)
    }

}