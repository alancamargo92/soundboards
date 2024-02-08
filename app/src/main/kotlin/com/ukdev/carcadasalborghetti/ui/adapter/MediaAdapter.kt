package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ukdev.carcadasalborghetti.databinding.ItemMediaBinding
import com.ukdev.carcadasalborghetti.ui.adapter.diffcallback.MediaDiffCallback
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.MediaViewHolder
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import java.util.Locale

class MediaAdapter(
    private val onItemClicked: (UiMedia) -> Unit,
    private val onItemLongClicked: (UiMedia) -> Unit
) : ListAdapter<UiMedia, MediaViewHolder>(MediaDiffCallback()) {

    fun filter(mediaList: List<UiMedia>, searchTerm: String?) {
        searchTerm?.lowercase(Locale.getDefault())?.let { query ->
            val filteredList = mediaList.filter { it.title.lowercase().contains(query) }
            submitList(filteredList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMediaBinding.inflate(inflater, parent, false)
        return MediaViewHolder(onItemClicked, onItemLongClicked, binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = getItem(position)
        holder.bindTo(media)
    }
}
