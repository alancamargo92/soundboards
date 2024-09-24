package com.ukdev.carcadasalborghetti.ui.navigation

import android.content.Context
import com.ukdev.carcadasalborghetti.navigation.VideoActivityNavigation
import com.ukdev.carcadasalborghetti.ui.VideoActivity
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import javax.inject.Inject

class VideoActivityNavigationImpl @Inject constructor() : VideoActivityNavigation {

    override fun startActivity(context: Context, media: UiMedia) {
        val args = VideoActivity.Args(media)
        val intent = VideoActivity.getIntent(context, args)
        context.startActivity(intent)
    }
}
