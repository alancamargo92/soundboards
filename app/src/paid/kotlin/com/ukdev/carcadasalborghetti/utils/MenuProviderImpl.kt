package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import android.widget.Toast
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.data.MediaLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuProviderImpl(
        context: Context,
        private val localDataSource: MediaLocalDataSource
) : MenuProvider(context) {

    override fun getMenuItemsAndActions(): Map<Int, () -> Unit> {
        return defaultItemsAndActions.apply {
            put(R.id.item_clear_cache) { clearCache() }
        }
    }

    private fun clearCache() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                localDataSource.clearCache()
            }
            Toast.makeText(context, R.string.cache_cleared, Toast.LENGTH_SHORT).show()
        }
    }

}