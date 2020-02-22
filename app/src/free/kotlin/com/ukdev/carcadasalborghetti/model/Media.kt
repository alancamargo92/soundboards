package com.ukdev.carcadasalborghetti.model

import android.net.Uri

data class Media(val title: String, val uri: Uri) {
    var position: Int = 0
}