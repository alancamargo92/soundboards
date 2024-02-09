package com.ukdev.carcadasalborghetti.ui.tools

import android.content.Context
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper
import com.ukdev.carcadasalborghetti.core.tools.ToastHelper
import com.ukdev.carcadasalborghetti.domain.cache.CacheManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MenuProviderImpl @Inject constructor(
    private val cacheManager: CacheManager,
    private val toastHelper: ToastHelper,
    dialogueHelper: DialogueHelper
) : MenuProvider(dialogueHelper) {

    override fun getMenuItemsAndActions(): Map<Int, (Context) -> Unit> {
        return defaultItemsAndActions.apply {
            put(R.id.item_clear_cache, ::clearCache)
        }
    }

    private fun clearCache(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                cacheManager.clearCache()
            }
            toastHelper.showToast(context, R.string.cache_cleared)
        }
    }
}
