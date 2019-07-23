package com.ukdev.carcadasalborghetti.listeners

import androidx.appcompat.widget.SearchView
import com.ukdev.carcadasalborghetti.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.model.Media

class QueryListener<T: Media>(private val adapter: MediaAdapter<T>,
                              private val medias: List<T>) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(medias, newText)
        return false
    }
}