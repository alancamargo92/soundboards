package com.ukdev.carcadasalborghetti.framework.tools

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager

class CrashReportManagerImpl : CrashReportManager {

    override fun logException(t: Throwable) {
        Log.e("ERROR_CARCADAS", t.message, t)
        Firebase.crashlytics.recordException(t)
    }

}