package com.ukdev.carcadasalborghetti.utils

import com.crashlytics.android.Crashlytics

class CrashReportManagerImpl : CrashReportManager {

    override fun logException(t: Throwable) {
        Crashlytics.logException(t)
    }

}