package com.ukdev.carcadasalborghetti.ui.listeners

import androidx.appcompat.widget.SearchView
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter

class QueryListener(
    private val adapter: MediaAdapter,
    private val mediaList: List<MediaV2>
) : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(mediaList, newText)
        return false
    }
}
