package com.ukdev.carcadasalborghetti.framework.media

import android.net.Uri

interface VideoHelper {
    fun playVideo(link: Uri, title: String)
}