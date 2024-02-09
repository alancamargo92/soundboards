package com.ukdev.carcadasalborghetti.ui.tools

import android.content.Context
import androidx.annotation.IdRes

data class MenuItemActionPair(@IdRes val itemId: Int, val action: (Context) -> Unit)
