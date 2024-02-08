package com.ukdev.carcadasalborghetti.ui.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiMedia(
    val uri: Uri,
    val title: String,
    val type: UiMediaType
) : Parcelable
