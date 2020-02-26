package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.ukdev.carcadasalborghetti.R

abstract class MenuProvider(protected val context: Context) {

    protected val defaultItemsAndActions = mutableMapOf(
            R.id.item_about to { showAppInfo() },
            R.id.item_privacy to { showPrivacyPolicy() }
    )

    abstract fun getMenuItemsAndActions(): Map<Int, () -> Unit>

    private fun showAppInfo() {
        val appName = context.getAppName()
        val appVersion = context.getAppVersion()
        val title = context.getString(R.string.app_info, appName, appVersion)
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(R.string.developer_info)
                .setNeutralButton(R.string.ok, null)
                .setIcon(R.mipmap.ic_launcher)
                .show()
    }

    private fun showPrivacyPolicy() {
        AlertDialog.Builder(context)
                .setView(R.layout.dialogue_privacy_terms)
                .setNeutralButton(R.string.ok, null)
                .show()
    }

}