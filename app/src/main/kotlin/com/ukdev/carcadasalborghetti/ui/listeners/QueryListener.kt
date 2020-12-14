package com.ukdev.carcadasalborghetti.ui.listeners

import androidx.appcompat.widget.SearchView
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.domain.entities.Media

class QueryListener(private val adapter: MediaAdapter,
                    private val media: List<Media>) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(media, newText)
        return false
    }
}