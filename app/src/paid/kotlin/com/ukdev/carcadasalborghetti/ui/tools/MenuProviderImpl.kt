package com.ukdev.carcadasalborghetti.ui.tools

import android.content.Context
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper
import com.ukdev.carcadasalborghetti.core.tools.ToastHelper
import com.ukdev.carcadasalborghetti.domain.cache.CacheManager
import javax.inject.Inject

class MenuProviderImpl @Inject constructor(
    private val cacheManager: CacheManager,
    private val toastHelper: ToastHelper,
    dialogueHelper: DialogueHelper
) : MenuProvider(dialogueHelper) {

    override fun getMenuItemsAndActions(): List<MenuItemActionPair> {
        return defaultItemsAndActions.apply {
            val action: (Context) -> Unit = {
                cacheManager.clearCache()
                toastHelper.showToast(R.string.cache_cleared)
            }
            val pair = MenuItemActionPair(R.id.item_clear_cache, action)
            add(pair)
        }
    }
}
