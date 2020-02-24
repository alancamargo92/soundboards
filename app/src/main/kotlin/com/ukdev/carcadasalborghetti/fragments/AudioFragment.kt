package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.View
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.AudioAdapter
import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.model.MediaType
import kotlinx.android.synthetic.main.fragment_audio.*
import org.koin.android.ext.android.inject

class AudioFragment : MediaListFragment(R.layout.fragment_audio, MediaType.AUDIO) {

    override val mediaHandler by inject<AudioHandler>()
    override val adapter = AudioAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { mediaHandler.stop() }
    }

    override fun onPlaybackStarted() {
        fab.show()
    }

    override fun onPlaybackStopped() {
        fab.hide()
    }

}