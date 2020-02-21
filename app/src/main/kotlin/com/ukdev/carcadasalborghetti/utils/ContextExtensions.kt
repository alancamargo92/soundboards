package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import com.ukdev.carcadasalborghetti.R

fun Context.getAppName(): String = getString(R.string.app_name)

fun Context.getAppVersion(): String = packageManager.getPackageInfo(packageName, 0).versionName