package com.ukdev.carcadasalborghetti.utils

import android.content.Context

class MenuProviderImpl(context: Context) : MenuProvider(context) {

    override fun getMenuItemsAndActions(): Map<Int, () -> Unit> {
        return defaultItemsAndActions
    }

}