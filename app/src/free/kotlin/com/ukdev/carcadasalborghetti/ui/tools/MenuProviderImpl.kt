package com.ukdev.carcadasalborghetti.ui.tools

import android.app.Activity
import javax.inject.Inject

class MenuProviderImpl @Inject constructor() : MenuProvider() {

    override fun getMenuItemsAndActions(): Map<Int, (activity: Activity) -> Unit> {
        return defaultItemsAndActions
    }
}
