package com.ukdev.carcadasalborghetti.ui.fragments

import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.adapter.VideoAdapter
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.framework.media.VideoHandler
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import org.koin.android.ext.android.inject

class VideoFragment : MediaListFragment(R.layout.layout_list, MediaType.VIDEO) {

    override val mediaHandler by inject<VideoHandler>()
    override val adapter: MediaAdapter = VideoAdapter()

    override fun showFab() { }

    override fun hideFab() { }

}