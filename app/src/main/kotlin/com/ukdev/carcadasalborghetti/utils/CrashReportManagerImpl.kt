package com.ukdev.carcadasalborghetti.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashReportManagerImpl : CrashReportManager {

    override fun logException(t: Throwable) {
        Log.e("ERROR_SOUNDBOARDS", t.message, t)
        FirebaseCrashlytics.getInstance().recordException(t)
    }

}