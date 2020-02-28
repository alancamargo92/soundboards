package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.View
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.FavouritesAdapter
import com.ukdev.carcadasalborghetti.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.handlers.FavouritesHandler
import com.ukdev.carcadasalborghetti.model.MediaType
import kotlinx.android.synthetic.main.fragment_audio.*
import org.koin.android.ext.android.inject

class FavouritesFragment : MediaListFragment(R.layout.fragment_audio, MediaType.BOTH) {

    override val mediaHandler by inject<FavouritesHandler>()
    override val adapter: MediaAdapter = FavouritesAdapter()

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