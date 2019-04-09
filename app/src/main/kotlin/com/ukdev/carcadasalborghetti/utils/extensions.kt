package com.ukdev.carcadasalborghetti.utils

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.ukdev.carcadasalborghetti.R
import kotlin.reflect.KClass

fun <T: ViewModel> AppCompatActivity.provideViewModel(clazz: KClass<T>) = lazy {
    ViewModelProviders.of(this).get(clazz.java)
}

fun Context.getAppName(): String = getString(R.string.app_name)

fun Context.getAppVersion(): String = packageManager.getPackageInfo(packageName, 0).versionName

@TargetApi(M)
fun AppCompatActivity.hasStoragePermissions(): Boolean {
    return hasPermission(READ_EXTERNAL_STORAGE) && hasPermission(WRITE_EXTERNAL_STORAGE)
}

@TargetApi(M)
fun AppCompatActivity.requestStoragePermissions(requestCode: Int) {
    requestPermissions(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), requestCode)
}

@TargetApi(M)
private fun AppCompatActivity.hasPermission(permission: String): Boolean {
    return checkSelfPermission(permission) == PERMISSION_GRANTED
}