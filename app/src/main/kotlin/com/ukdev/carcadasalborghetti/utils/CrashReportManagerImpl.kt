package com.ukdev.carcadasalborghetti.utils

import com.crashlytics.android.Crashlytics

class CrashReportManagerImpl : CrashReportManager {

    override fun log(message: String) {
        Crashlytics.log(message)
    }

    override fun logException(t: Throwable) {
        Crashlytics.logException(t)
    }

}