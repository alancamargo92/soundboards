package com.ukdev.carcadasalborghetti.listeners

import com.ukdev.carcadasalborghetti.model.Media

interface RecyclerViewInteractionListener {
    fun onItemClick(media: Media)
    fun onItemLongClick(media: Media)
}