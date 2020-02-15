package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.ukdev.carcadasalborghetti.R
import kotlin.reflect.KClass

fun <T: ViewModel> Fragment.provideViewModel(clazz: KClass<T>) = lazy {
    ViewModelProviders.of(this).get(clazz.java)
}

fun Context.getAppName(): String = getString(R.string.app_name)

fun Context.getAppVersion(): String = packageManager.getPackageInfo(packageName, 0).versionName