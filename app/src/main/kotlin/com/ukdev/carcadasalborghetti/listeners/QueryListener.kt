package com.ukdev.carcadasalborghetti.listeners

import androidx.appcompat.widget.SearchView
import com.ukdev.carcadasalborghetti.adapter.CarcadaAdapter
import com.ukdev.carcadasalborghetti.model.Carcada

class QueryListener(private val adapter: CarcadaAdapter,
                    private val carcadas: List<Carcada>) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(carcadas, newText)
        return false
    }
}