package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.ukdev.carcadasalborghetti.R

fun Context.getAppName(): String = getString(R.string.app_name)

fun Context.getAppVersion(): String = packageManager.getPackageInfo(packageName, 0).versionName

fun View.isVisible(): Boolean {
    return visibility == VISIBLE
}

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}