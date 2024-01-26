package com.ukdev.carcadasalborghetti.data.tools

import android.util.Log
import javax.inject.Inject

private const val TAG = "LOG_CARCADAS"

class LoggerImpl @Inject constructor() : Logger {

    override fun debug(message: String) {
        Log.d(TAG, message)
    }

    override fun error(t: Throwable) {
        Log.e(TAG, t.message, t)
    }
}
