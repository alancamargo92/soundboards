package com.ukdev.carcadasalborghetti.data.tools

interface Logger {

    fun debug(message: String)

    fun error(t: Throwable)
}
