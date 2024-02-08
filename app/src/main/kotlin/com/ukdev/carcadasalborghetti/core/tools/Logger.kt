package com.ukdev.carcadasalborghetti.core.tools

interface Logger {

    fun debug(message: String)

    fun error(t: Throwable)
}