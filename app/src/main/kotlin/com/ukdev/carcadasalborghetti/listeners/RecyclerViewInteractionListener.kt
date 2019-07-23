package com.ukdev.carcadasalborghetti.listeners

import com.ukdev.carcadasalborghetti.model.Media

interface RecyclerViewInteractionListener<T: Media> {
    fun onItemClick(media: T)
    fun onItemLongClick(media: T)
}