package com.ukdev.carcadasalborghetti.ui.media

import android.content.Context
import android.net.Uri
import com.ukdev.carcadasalborghetti.ui.VideoActivity
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiMediaType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VideoHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : VideoHelper {

    override fun playVideo(link: Uri, title: String) {
        val media = UiMedia(
            uri = link,
            title = title,
            type = UiMediaType.VIDEO
        )
        val intent = VideoActivity.getIntent(context, media)
        context.startActivity(intent)
    }
}
