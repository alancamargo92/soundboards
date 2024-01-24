package com.ukdev.carcadasalborghetti.ui.media

import android.content.Context
import android.net.Uri
import com.ukdev.carcadasalborghetti.ui.activities.VideoActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VideoHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : VideoHelper {

    override fun playVideo(link: Uri, title: String) {
        val intent = VideoActivity.getIntent(context, title, link)
        context.startActivity(intent)
    }

}