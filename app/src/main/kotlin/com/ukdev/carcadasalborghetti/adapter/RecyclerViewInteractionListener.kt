package com.ukdev.carcadasalborghetti.adapter

import com.ukdev.carcadasalborghetti.model.Carcada

interface RecyclerViewInteractionListener {
    fun onItemClick(carcada: Carcada)
    fun onItemLongClick(carcada: Carcada)
}