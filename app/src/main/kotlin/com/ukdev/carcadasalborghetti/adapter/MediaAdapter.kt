package com.ukdev.carcadasalborghetti.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Media
import java.util.*

abstract class MediaAdapter : RecyclerView.Adapter<MediaViewHolder>() {

    protected var data: List<Media>? = null

    private lateinit var holder: MediaViewHolder

    private var listener: RecyclerViewInteractionListener? = null

    fun submitData(data: List<Media>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setListener(listener: RecyclerViewInteractionListener) {
        this.listener = listener
    }

    fun filter(media: List<Media>, searchTerm: String?) {
        searchTerm?.toLowerCase(Locale.getDefault())?.let { query ->
            data = media.filter { it.title.toLowerCase(Locale.getDefault()).contains(query) }
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
            with(holder) {
                bindTo(media)

                itemView.setOnClickListener {
                    this@MediaAdapter.holder = holder
                    notifyItemClicked()
                    listener?.onItemClick(media)
                }

                itemView.setOnLongClickListener {
                    this@MediaAdapter.holder = holder
                    notifyItemClicked()
                    listener?.onItemLongClick(media)
                    true
                }
            }
        }
    }

    override fun getItemCount() = data?.size ?: 0

}