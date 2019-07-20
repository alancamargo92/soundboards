package com.ukdev.carcadasalborghetti.listeners

import com.ukdev.carcadasalborghetti.model.Audio

interface RecyclerViewInteractionListener {
    fun onItemClick(audio: Audio)
    fun onItemLongClick(audio: Audio)
}