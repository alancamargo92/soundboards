package com.ukdev.carcadasalborghetti.ui.tools

import android.content.Context
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.extensions.getAppName
import com.ukdev.carcadasalborghetti.core.extensions.getAppVersion
import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper

abstract class MenuProvider(private val dialogueHelper: DialogueHelper) {

    protected val defaultItemsAndActions = mutableListOf(
        MenuItemActionPair(R.id.item_about, ::showAppInfo),
        MenuItemActionPair(R.id.item_privacy, ::showPrivacyPolicy)
    )

    abstract fun getMenuItemsAndActions(): List<MenuItemActionPair>

    private fun showAppInfo(context: Context) {
        val appName = context.getAppName()
        val appVersion = context.getAppVersion()
        val title = context.getString(R.string.app_info, appName, appVersion)

        dialogueHelper.showDialogue(
            context = context,
            title = title,
            messageRes = R.string.developer_info
        )
    }

    private fun showPrivacyPolicy(context: Context) {
        dialogueHelper.showDialogue(
            context = context,
            titleRes = R.string.privacy_terms_title,
            messageRes = R.string.privacy_terms
        )
    }
}
