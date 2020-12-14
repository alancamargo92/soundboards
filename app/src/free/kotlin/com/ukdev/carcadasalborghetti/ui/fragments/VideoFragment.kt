package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.VideoAdapter
import com.ukdev.carcadasalborghetti.framework.media.VideoHandler
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import kotlinx.android.synthetic.free.fragment_video.*
import org.koin.android.ext.android.inject

open class VideoFragment : MediaListFragment(R.layout.fragment_video, MediaType.VIDEO) {

    override val mediaHandler by inject<VideoHandler>()
    override val adapter = VideoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        txt_paid_version.text = getString(R.string.get_paid_version_rationale)
        bt_paid_version.setOnClickListener {
            showPaidVersion()
        }
    }

    override fun showFab() { }

    override fun hideFab() { }

    private fun showPaidVersion() {
        val url = "https://play.google.com/store/apps/details?id=com.ukdev.carcadasalborghetti.paid"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}