package com.ukdev.carcadasalborghetti.listeners

import com.ukdev.carcadasalborghetti.model.Audio

interface RecyclerViewInteractionListener {
    fun onItemClick(audio: Audio) // TODO: refactor to media
    fun onItemLongClick(audio: Audio)
}