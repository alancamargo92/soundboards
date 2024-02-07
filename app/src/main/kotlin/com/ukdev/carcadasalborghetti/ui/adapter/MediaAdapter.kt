package com.ukdev.carcadasalborghetti.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ukdev.carcadasalborghetti.databinding.ItemAudioBinding
import com.ukdev.carcadasalborghetti.databinding.ItemVideoBinding
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.ui.adapter.diffcallback.MediaDiffCallback
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.AudioViewHolder
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.MediaViewHolder
import com.ukdev.carcadasalborghetti.ui.adapter.viewholder.VideoViewHolder
import java.util.Locale

private const val VIEW_TYPE_AUDIO = 0
private const val VIEW_TYPE_VIDEO = 1

class MediaAdapter(
    private val onItemClicked: (MediaV2) -> Unit,
    private val onItemLongClicked: (MediaV2) -> Unit
) : ListAdapter<MediaV2, MediaViewHolder>(MediaDiffCallback()) {

    fun filter(mediaList: List<MediaV2>, searchTerm: String?) {
        searchTerm?.lowercase(Locale.getDefault())?.let { query ->
            val filteredList = mediaList.filter { it.title.lowercase().contains(query) }
            submitList(filteredList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_AUDIO -> {
                val binding = ItemAudioBinding.inflate(inflater, parent, false)
                AudioViewHolder(onItemClicked, onItemLongClicked, binding)
            }

            VIEW_TYPE_VIDEO -> {
                val binding = ItemVideoBinding.inflate(inflater, parent, false)
                VideoViewHolder(onItemClicked, onItemLongClicked, binding)
            }

            else -> error("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = getItem(position)
        holder.bindTo(media)
    }

    override fun getItemViewType(position: Int): Int {
        val media = getItem(position)
        return when (media.type) {
            MediaTypeV2.AUDIO -> VIEW_TYPE_AUDIO
            MediaTypeV2.VIDEO -> VIEW_TYPE_VIDEO
        }
    }
}
