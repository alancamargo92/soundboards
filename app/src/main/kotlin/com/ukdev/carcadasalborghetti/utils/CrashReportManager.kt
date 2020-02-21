package com.ukdev.carcadasalborghetti.utils

interface CrashReportManager {
    fun log(message: String)
    fun logException(t: Throwable)
}