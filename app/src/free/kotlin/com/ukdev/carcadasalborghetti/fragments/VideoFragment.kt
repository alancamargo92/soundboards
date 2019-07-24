package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.View
import com.ukdev.carcadasalborghetti.adapter.VideoAdapter
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.model.Video
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.viewmodel.VideoViewModel
import kotlinx.android.synthetic.free.fragment_video.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class VideoFragment : MediaListFragment<Video>(0, 0) {

    override val mediaHandler by inject<VideoHandler> { parametersOf(this) }
    override val viewModel by provideViewModel(VideoViewModel::class)
    override val adapter = VideoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_paid_version.setOnClickListener {
            showPaidVersion()
        }
    }

    override fun onStartPlayback() { }

    override fun onStopPlayback() { }

    private fun showPaidVersion() {

    }

}