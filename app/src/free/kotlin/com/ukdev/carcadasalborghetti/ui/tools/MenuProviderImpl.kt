package com.ukdev.carcadasalborghetti.ui.tools

import android.app.Activity
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider

class MenuProviderImpl : MenuProvider() {

    override fun getMenuItemsAndActions(): Map<Int, (activity: Activity) -> Unit> {
        return defaultItemsAndActions
    }

}