package com.ukdev.carcadasalborghetti.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Audio

class AudioAdapter : RecyclerView.Adapter<AudioViewHolder>() {

    private var data: List<Audio>? = null
    private var listener: RecyclerViewInteractionListener<Audio>? = null

    fun setData(data: List<Audio>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setListener(listener: RecyclerViewInteractionListener<Audio>) {
        this.listener = listener
    }

    fun filter(audios: List<Audio>, searchTerm: String?) {
        searchTerm?.toLowerCase()?.let { query ->
            data = audios.filter { it.title.toLowerCase().contains(query) }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_audio, parent, false)
        return AudioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        data?.get(position).let {
            it?.let { audio ->
                holder.run {
                    bindTo(audio)
                    holder.itemView.setOnClickListener { listener?.onItemClick(audio) }
                    holder.itemView.setOnLongClickListener {
                        listener?.run {
                            onItemLongClick(audio)
                            return@setOnLongClickListener true
                        }
                        false
                    }
                }
            }
        }
    }

    override fun getItemCount() = data?.size ?: 0

}