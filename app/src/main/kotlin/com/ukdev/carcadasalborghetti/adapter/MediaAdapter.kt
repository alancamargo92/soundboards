package com.ukdev.carcadasalborghetti.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Media

abstract class MediaAdapter : RecyclerView.Adapter<MediaViewHolder>() {

    private lateinit var holder: MediaViewHolder

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

    fun notifyItemClicked() {
        holder.notifyItemClicked()
    }

    fun notifyItemReady() {
        holder.notifyItemReady()
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        data?.get(position)?.let { media ->
            holder.run {
                bindTo(media)

                itemView.setOnClickListener {
                    this@MediaAdapter.holder = holder
                    notifyItemClicked()
                    listener?.onItemClick(media)
                }

                itemView.setOnLongClickListener {
                    this@MediaAdapter.holder = holder
                    listener?.run {
                        notifyItemClicked()
                        onItemLongClick(media)
                        return@setOnLongClickListener true
                    }
                    false
                }
            }
        }
    }

    override fun getItemCount() = data?.size ?: 0

}