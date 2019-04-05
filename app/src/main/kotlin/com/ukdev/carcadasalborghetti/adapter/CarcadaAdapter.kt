package com.ukdev.carcadasalborghetti.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Carcada

class CarcadaAdapter(
        private val carcadas: List<Carcada>
) : RecyclerView.Adapter<CarcadaViewHolder>() {

    private var onItemClickListener: ((carcada: Carcada) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarcadaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val containerView = inflater.inflate(R.layout.item_carcada, parent, false)
        return CarcadaViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: CarcadaViewHolder, position: Int) {
        carcadas[position].let { carcada ->
            holder.bindTo(carcada)
            holder.containerView.setOnClickListener { onItemClickListener?.invoke(carcada) }
        }
    }

    override fun getItemCount() = carcadas.size

}