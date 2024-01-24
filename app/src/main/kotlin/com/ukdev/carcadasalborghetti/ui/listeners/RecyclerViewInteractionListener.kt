package com.ukdev.carcadasalborghetti.ui.listeners

import com.ukdev.carcadasalborghetti.domain.model.Media

interface RecyclerViewInteractionListener {
    fun onItemClick(media: Media)
    fun onItemLongClick(media: Media)
}