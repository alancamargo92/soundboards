package com.ukdev.carcadasalborghetti.core.tools

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val FILE_NAME = "preferences"
private const val KEY_SHOW_TIP = "show_tip"

class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferencesManager {

    private val preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    override fun shouldShowTip(): Boolean {
        return preferences.getBoolean(KEY_SHOW_TIP, true)
    }

    override fun disableTip() {
        preferences.edit().putBoolean(KEY_SHOW_TIP, false).apply()
    }
}