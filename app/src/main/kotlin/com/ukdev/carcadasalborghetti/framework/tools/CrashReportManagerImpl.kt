package com.ukdev.carcadasalborghetti.framework.tools

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager

class CrashReportManagerImpl : CrashReportManager {

    override fun logException(t: Throwable) {
        Log.e("ERROR_SOUNDBOARDS", t.message, t)
        FirebaseCrashlytics.getInstance().recordException(t)
    }

}