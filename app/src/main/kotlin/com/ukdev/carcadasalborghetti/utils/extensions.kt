package com.ukdev.carcadasalborghetti.utils

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION_CODES.M
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import kotlin.reflect.KClass

fun <M: Media, T: MediaViewModel<M>> Fragment.provideViewModel(clazz: KClass<T>) = lazy {
    ViewModelProviders.of(this).get(clazz.java)
}

fun Context.getAppName(): String = getString(R.string.app_name)

fun Context.getAppVersion(): String = packageManager.getPackageInfo(packageName, 0).versionName

@TargetApi(M)
fun Fragment.hasStoragePermissions(): Boolean {
    return hasPermission(READ_EXTERNAL_STORAGE) && hasPermission(WRITE_EXTERNAL_STORAGE)
}

@TargetApi(M)
fun Fragment.requestStoragePermissions(requestCode: Int) {
    requestPermissions(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), requestCode)
}

@TargetApi(M)
private fun Fragment.hasPermission(permission: String): Boolean {
    return checkSelfPermission(requireContext(), permission) == PERMISSION_GRANTED
}