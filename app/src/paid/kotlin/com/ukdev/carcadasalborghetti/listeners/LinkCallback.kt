package com.ukdev.carcadasalborghetti.listeners

interface LinkCallback {
    fun onLinkReady(link: String, title: String)
    fun onError()
}