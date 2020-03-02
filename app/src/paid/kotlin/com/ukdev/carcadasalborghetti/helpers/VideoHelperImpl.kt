package com.ukdev.carcadasalborghetti.helpers

import android.content.Context
import android.net.Uri
import com.ukdev.carcadasalborghetti.activities.VideoActivity

class VideoHelperImpl(private val context: Context) : VideoHelper {

    override fun playVideo(link: Uri, title: String) {
        val intent = VideoActivity.getIntent(context, title, link)
        context.startActivity(intent)
    }

}