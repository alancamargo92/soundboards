package com.ukdev.carcadasalborghetti.helpers

import android.content.Context

class PreferencesHelperImpl(context: Context) : PreferencesHelper {

    private val preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    override fun shouldShowTip(): Boolean {
        return preferences.getBoolean(KEY_SHOW_TIP, true)
    }

    override fun disableTip() {
        preferences.edit().putBoolean(KEY_SHOW_TIP, false).apply()
    }

    private companion object {
        const val FILE_NAME = "preferences"
        const val KEY_SHOW_TIP = "show_tip"
    }

}