package com.ukdev.carcadasalborghetti.core.extensions

import android.os.Build
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

const val KEY_ARGS = "key-args"

fun <F : Fragment> F.putArguments(args: Parcelable): F = also {
    it.arguments = bundleOf(KEY_ARGS to args)
}

inline fun <reified A : Parcelable> Fragment.args() = lazy {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        @Suppress("DEPRECATION")
        arguments?.getParcelable(KEY_ARGS)
    } else {
        arguments?.getParcelable(KEY_ARGS, A::class.java)
    } ?: error("Arguments not provided")
}
