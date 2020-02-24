package com.ukdev.carcadasalborghetti.helpers

import android.content.Context
import com.ukdev.carcadasalborghetti.activities.VideoActivity

class VideoHelperImpl(private val context: Context) : VideoHelper {

    override fun playVideo(link: String, title: String) {
        val intent = VideoActivity.getIntent(context, title, link)
        context.startActivity(intent)
    }

}