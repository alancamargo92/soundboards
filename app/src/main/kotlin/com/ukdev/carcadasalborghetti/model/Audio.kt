package com.ukdev.carcadasalborghetti.model

import android.net.Uri

data class Audio(
        override val title: String,
        override val length: String,
        override var position: Int,
        override val uri: Uri
) : Media(title, length, position, uri)