package com.ukdev.carcadasalborghetti.core.tools

import android.content.Context
import android.widget.Toast
import javax.inject.Inject

class ToastHelperImpl @Inject constructor() : ToastHelper {

    override fun showToast(context: Context, messageRes: Int) {
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
    }
}
