package com.ukdev.carcadasalborghetti.framework.tools

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ukdev.carcadasalborghetti.data.tools.Logger
import javax.inject.Inject

class LoggerImpl @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) : Logger {

    override fun logException(t: Throwable) {
        Log.e("ERROR_CARCADAS", t.message, t)
        crashlytics.recordException(t)
    }
}
