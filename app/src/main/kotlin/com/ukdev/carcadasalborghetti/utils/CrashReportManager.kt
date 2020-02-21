package com.ukdev.carcadasalborghetti.utils

interface CrashReportManager {
    fun logException(t: Throwable)
}