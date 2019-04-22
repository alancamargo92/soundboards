package com.ukdev.carcadasalborghetti.utils

import android.content.Context

class PreferenceUtils(context: Context?) {

    private val preferences = context?.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    fun shouldShowTip() = preferences?.getBoolean(KEY_SHOW_TIP, true)

    fun disableTip() {
        preferences?.edit()?.putBoolean(KEY_SHOW_TIP, false)?.apply()
    }

    private companion object {
        const val KEY_SHOW_TIP = "show_tip"
    }

}