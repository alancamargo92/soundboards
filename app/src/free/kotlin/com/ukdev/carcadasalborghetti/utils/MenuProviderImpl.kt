package com.ukdev.carcadasalborghetti.utils

import android.app.Activity

class MenuProviderImpl : MenuProvider() {

    override fun getMenuItemsAndActions(): Map<Int, (activity: Activity) -> Unit> {
        return defaultItemsAndActions
    }

}