package com.ukdev.carcadasalborghetti.core.tools

import android.content.Context
import androidx.annotation.StringRes

interface DialogueHelper {

    fun showDialogue(
        context: Context,
        title: String,
        @StringRes messageRes: Int
    )

    fun showDialogue(
        context: Context,
        @StringRes titleRes: Int,
        @StringRes messageRes: Int
    )

    fun showDialogue(
        context: Context,
        @StringRes titleRes: Int,
        @StringRes messageRes: Int,
        @StringRes positiveButtonTextRes: Int,
        onPositiveButtonClicked: () -> Unit
    )
}
