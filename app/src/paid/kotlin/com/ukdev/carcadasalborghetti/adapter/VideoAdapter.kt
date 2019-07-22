package com.ukdev.carcadasalborghetti.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Video

class VideoAdapter : RecyclerView.Adapter<VideoViewHolder>() {

    private var data: List<Video>? = null
    private var listener: RecyclerViewInteractionListener? = null

    fun setData(data: List<Video>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setListener(listener: RecyclerViewInteractionListener) {
        this.listener = listener
    }

    fun filter(videos: List<Video>, searchTerm: String?) {
        searchTerm?.toLowerCase()?.let { query ->
            data = videos.filter { it.title.toLowerCase().contains(query) }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        data?.get(position).let {
            it?.let { video ->
                holder.run {
                    bindTo(video)
                    //holder.itemView.setOnClickListener { listener?.onItemClick(video) }
                    holder.itemView.setOnLongClickListener {
                        listener?.run {
                            //onItemLongClick(video) TODO: uncomment once it's ready for videos
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
