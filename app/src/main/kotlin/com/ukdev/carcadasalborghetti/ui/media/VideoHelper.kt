package com.ukdev.carcadasalborghetti.ui.media

import android.net.Uri

interface VideoHelper {
    fun playVideo(link: Uri, title: String)
}