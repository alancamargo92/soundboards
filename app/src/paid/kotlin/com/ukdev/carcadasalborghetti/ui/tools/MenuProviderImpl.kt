package com.ukdev.carcadasalborghetti.ui.tools

import android.app.Activity
import android.widget.Toast
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuProviderImpl(private val localDataSource: MediaLocalDataSource) : MenuProvider() {

    override fun getMenuItemsAndActions(): Map<Int, (activity: Activity) -> Unit> {
        return defaultItemsAndActions.apply {
            put(R.id.item_clear_cache, ::clearCache)
        }
    }

    private fun clearCache(activity: Activity) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                localDataSource.clearCache()
            }
            Toast.makeText(activity, R.string.cache_cleared, Toast.LENGTH_SHORT).show()
        }
    }

}