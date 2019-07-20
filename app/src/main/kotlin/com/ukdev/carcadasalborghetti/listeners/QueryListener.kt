package com.ukdev.carcadasalborghetti.listeners

import androidx.appcompat.widget.SearchView
import com.ukdev.carcadasalborghetti.adapter.AudioAdapter
import com.ukdev.carcadasalborghetti.model.Audio

class QueryListener(private val adapter: AudioAdapter,
                    private val audio: List<Audio>) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(audio, newText)
        return false
    }
}