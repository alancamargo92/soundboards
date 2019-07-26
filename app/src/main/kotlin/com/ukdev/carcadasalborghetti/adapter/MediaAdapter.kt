package com.ukdev.carcadasalborghetti.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaAdapter : RecyclerView.Adapter<MediaViewHolder>() {

    private var data: List<Media>? = null
    private var listener: RecyclerViewInteractionListener? = null

    fun setData(data: List<Media>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setListener(listener: RecyclerViewInteractionListener) {
        this.listener = listener
    }

    fun filter(media: List<Media>, searchTerm: String?) {
        searchTerm?.toLowerCase()?.let { query ->
            data = media.filter { it.title.toLowerCase().contains(query) }
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
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