package com.ukdev.carcadasalborghetti.core.extensions

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_ARGS = "extra-args"

fun Intent.putArguments(args: Parcelable): Intent {
    return putExtra(EXTRA_ARGS, args)
}

inline fun <reified A : Parcelable> AppCompatActivity.args() = lazy {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        @Suppress("DEPRECATION")
        intent.getParcelableExtra(EXTRA_ARGS)
    } else {
        intent.getParcelableExtra(EXTRA_ARGS, A::class.java)
    } ?: error("Arguments not provided")
}
