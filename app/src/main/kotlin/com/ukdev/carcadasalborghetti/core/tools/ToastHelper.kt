package com.ukdev.carcadasalborghetti.core.tools

import android.content.Context
import androidx.annotation.StringRes

interface ToastHelper {

    fun showToast(context: Context, @StringRes messageRes: Int)
}
