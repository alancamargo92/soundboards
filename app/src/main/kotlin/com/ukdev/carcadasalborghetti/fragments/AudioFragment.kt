package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.AudioAdapter
import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.model.MediaType
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AudioFragment : MediaListFragment(R.layout.fragment_audio, MediaType.AUDIO) {

    override val mediaHandler by inject<AudioHandler> { parametersOf(this, this) }
    override val adapter = AudioAdapter()

    private var fab: FloatingActionButton? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = requireActivity().findViewById(R.id.fab)
        fab?.setOnClickListener { mediaHandler.stop() }
    }

    override fun onPlaybackStarted() {
        fab?.show()
    }

    override fun onPlaybackStopped() {
        fab?.hide()
    }

}