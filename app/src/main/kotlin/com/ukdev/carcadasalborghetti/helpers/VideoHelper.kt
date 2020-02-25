package com.ukdev.carcadasalborghetti.helpers

import android.net.Uri

interface VideoHelper {
    fun playVideo(link: Uri, title: String)
}