package com.ukdev.carcadasalborghetti.ui.fragments

import android.os.Bundle
import android.view.View
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.adapter.AudioAdapter
import com.ukdev.carcadasalborghetti.framework.media.AudioHandler
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import kotlinx.android.synthetic.main.fragment_audio.*
import org.koin.android.ext.android.inject

class AudioFragment : MediaListFragment(R.layout.fragment_audio, MediaType.AUDIO) {

    override val mediaHandler by inject<AudioHandler>()
    override val adapter = AudioAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { mediaHandler.stop() }
    }

    override fun showFab() {
        fab.show()
    }

    override fun hideFab() {
        fab.hide()
    }

}