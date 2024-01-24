package com.ukdev.carcadasalborghetti.ui.tools

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ukdev.carcadasalborghetti.R

abstract class MenuProvider {

    protected val defaultItemsAndActions = mutableMapOf(
            R.id.item_about to ::showAppInfo,
            R.id.item_privacy to ::showPrivacyPolicy
    )

    abstract fun getMenuItemsAndActions(): Map<Int, (activity: Activity) -> Unit>

    private fun showAppInfo(activity: Activity) {
        val appName = activity.getAppName()
        val appVersion = activity.getAppVersion()
        val title = activity.getString(R.string.app_info, appName, appVersion)

        MaterialAlertDialogBuilder(activity)
                .setTitle(title)
                .setMessage(R.string.developer_info)
                .setNeutralButton(R.string.ok, null)
                .setIcon(R.mipmap.ic_launcher)
                .show()
    }

    private fun showPrivacyPolicy(activity: Activity) {
        MaterialAlertDialogBuilder(activity)
                .setTitle(R.string.privacy_terms_title)
                .setMessage(R.string.privacy_terms)
                .setNeutralButton(R.string.ok, null)
                .show()
    }
}
