package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.VideoAdapter
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.model.MediaType
import kotlinx.android.synthetic.free.fragment_video.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class VideoFragment : MediaListFragment(MediaType.VIDEO, 0, 0) {

    override val mediaHandler by inject<VideoHandler> { parametersOf(this) }
    override val adapter = VideoAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        txt_paid_version.text = getString(R.string.get_paid_version_rationale)
        bt_paid_version.setOnClickListener {
            showPaidVersion()
        }
    }

    override fun onStartPlayback() { }

    override fun onStopPlayback() { }

    private fun showPaidVersion() {
        // TODO
        Toast.makeText(requireContext(), "Test", Toast.LENGTH_SHORT).show()
    }

}