package com.ukdev.carcadasalborghetti.listeners

import com.ukdev.carcadasalborghetti.model.Carcada

interface RecyclerViewInteractionListener {
    fun onItemClick(carcada: Carcada)
    fun onItemLongClick(carcada: Carcada)
}