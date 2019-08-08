package com.ukdev.carcadasalborghetti.listeners

import com.ukdev.carcadasalborghetti.model.ErrorType

interface LinkCallback {
    fun onLinkReady(link: String, title: String)
    fun onError(errorType: ErrorType)
}