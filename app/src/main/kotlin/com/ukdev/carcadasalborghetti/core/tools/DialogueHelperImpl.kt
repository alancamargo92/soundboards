package com.ukdev.carcadasalborghetti.core.tools

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ukdev.carcadasalborghetti.R
import javax.inject.Inject

class DialogueHelperImpl @Inject constructor() : DialogueHelper {

    override fun showDialogue(context: Context, title: String, messageRes: Int) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(messageRes)
            .setIcon(R.mipmap.ic_launcher)
            .setNeutralButton(R.string.ok, null)
            .show()
    }

    override fun showDialogue(context: Context, titleRes: Int, messageRes: Int) {
        MaterialAlertDialogBuilder(context)
            .setTitle(titleRes)
            .setMessage(messageRes)
            .setIcon(R.mipmap.ic_launcher)
            .setNeutralButton(R.string.ok, null)
            .show()
    }

    override fun showDialogue(
        context: Context,
        titleRes: Int,
        messageRes: Int,
        positiveButtonTextRes: Int,
        onPositiveButtonClicked: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(titleRes)
            .setMessage(messageRes)
            .setIcon(R.mipmap.ic_launcher)
            .setNeutralButton(R.string.ok, null)
            .setPositiveButton(positiveButtonTextRes) { _, _ ->
                onPositiveButtonClicked()
            }.show()
    }
}
