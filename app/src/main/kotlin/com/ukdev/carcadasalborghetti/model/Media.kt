package com.ukdev.carcadasalborghetti.model

import android.net.Uri

open class Media(
        open val title: String,
        open val length: String,
        open var position: Int,
        open val uri: Uri
)