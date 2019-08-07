package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.AudioAdapter
import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.model.MediaType
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AudioFragment : MediaListFragment(MediaType.AUDIO) {

    override val mediaHandler by inject<AudioHandler> { parametersOf(this) }
    override val adapter = AudioAdapter()

    private var fab: FloatingActionButton? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = requireActivity().findViewById(R.id.fab)
        fab?.setOnClickListener { mediaHandler.stop() }
    }

    override fun onStartPlayback() {
        fab?.visibility = VISIBLE
    }

    override fun onStopPlayback() {
        fab?.visibility = GONE
    }

}