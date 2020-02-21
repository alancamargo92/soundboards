package com.ukdev.carcadasalborghetti.fragments

import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.adapter.VideoAdapter
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.model.MediaType
import org.koin.android.ext.android.inject

class VideoFragment : MediaListFragment(R.layout.layout_list, MediaType.VIDEO) {

    override val mediaHandler by inject<VideoHandler>()
    override val adapter: MediaAdapter = VideoAdapter()

    override fun onPlaybackStarted() { }

    override fun onPlaybackStopped() { }

}