package com.ukdev.carcadasalborghetti.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Carcada

class CarcadaAdapter : RecyclerView.Adapter<CarcadaViewHolder>() {

    private var data: List<Carcada>? = null
    private var listener: RecyclerViewInteractionListener? = null

    fun setData(data: List<Carcada>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setListener(listener: RecyclerViewInteractionListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarcadaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val containerView = inflater.inflate(R.layout.item_carcada, parent, false)
        return CarcadaViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: CarcadaViewHolder, position: Int) {
        data?.get(position).let {
            it?.let { carcada ->
                holder.run {
                    bindTo(carcada)
                    containerView.setOnClickListener { listener?.onItemClick(carcada) }
                    containerView.setOnLongClickListener {
                        listener?.run {
                            onItemLongClick(carcada)
                            true
                        }
                        false
                    }
                }
            }
        }
    }

    override fun getItemCount() = data?.size ?: 0

}