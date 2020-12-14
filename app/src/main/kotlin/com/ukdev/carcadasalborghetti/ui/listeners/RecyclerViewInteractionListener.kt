package com.ukdev.carcadasalborghetti.ui.listeners

import com.ukdev.carcadasalborghetti.domain.entities.Media

interface RecyclerViewInteractionListener {
    fun onItemClick(media: Media)
    fun onItemLongClick(media: Media)
}