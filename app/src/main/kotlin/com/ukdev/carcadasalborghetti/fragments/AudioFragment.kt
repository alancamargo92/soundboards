package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.AudioAdapter
import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.model.Audio
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.viewmodel.AudioViewModel
import kotlinx.android.synthetic.main.fragment_audio.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AudioFragment : MediaListFragment<Audio>(ITEM_SPAN_PORTRAIT, ITEM_SPAN_LANDSCAPE) {

    override val mediaHandler by inject<AudioHandler> { parametersOf(this) }
    override val viewModel by provideViewModel(AudioViewModel::class)
    override val adapter = AudioAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { mediaHandler.stop() }
    }

    override fun onStartPlayback() {
        fab.visibility = VISIBLE
    }

    override fun onStopPlayback() {
        fab.visibility = GONE
    }

    companion object {
        private const val ITEM_SPAN_PORTRAIT = 3
        private const val ITEM_SPAN_LANDSCAPE = 4
    }

}