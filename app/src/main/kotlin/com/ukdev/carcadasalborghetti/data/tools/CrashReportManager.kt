package com.ukdev.carcadasalborghetti.data.tools

interface CrashReportManager {
    fun logException(t: Throwable)
}