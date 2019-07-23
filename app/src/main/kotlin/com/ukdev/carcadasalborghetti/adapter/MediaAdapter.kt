package com.ukdev.carcadasalborghetti.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaAdapter<T: Media> : RecyclerView.Adapter<MediaViewHolder<T>>() {

    private var data: List<T>? = null
    private var listener: RecyclerViewInteractionListener<T>? = null

    fun setData(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setListener(listener: RecyclerViewInteractionListener<T>) {
        this.listener = listener
    }

    fun filter(medias: List<T>, searchTerm: String?) {
        searchTerm?.toLowerCase()?.let { query ->
            data = medias.filter { it.title.toLowerCase().contains(query) }
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: MediaViewHolder<T>, position: Int) {
        data?.get(position)?.let { media ->
            holder.bindTo(media)
            holder.itemView.setOnClickListener { listener?.onItemClick(media) }
            holder.itemView.setOnLongClickListener {
                listener?.run {
                    onItemLongClick(media)
                    return@setOnLongClickListener true
                }
                false
            }
        }
    }

    override fun getItemCount() = data?.size ?: 0

}