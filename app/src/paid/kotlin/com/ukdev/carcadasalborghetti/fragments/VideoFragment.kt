package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.adapter.VideoAdapter
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.model.Video
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.viewmodel.VideoViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class VideoFragment : MediaListFragment<Video>(SPAN_COUNT_PORTRAIT, SPAN_COUNT_LANDSCAPE) {

    override val mediaHandler by inject<VideoHandler>{ parametersOf(this) }
    override val viewModel by provideViewModel(VideoViewModel::class)
    override val adapter: MediaAdapter<Video> = VideoAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_list, container, false)
    }

    override fun onStartPlayback() {

    }

    override fun onStopPlayback() {

    }

    companion object {
        private const val SPAN_COUNT_PORTRAIT = 2
        private const val SPAN_COUNT_LANDSCAPE = 3
    }

}