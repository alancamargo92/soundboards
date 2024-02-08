package com.ukdev.carcadasalborghetti.ui.listeners

import androidx.appcompat.widget.SearchView
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.ui.model.UiMedia

class QueryListener(
    private val adapter: MediaAdapter,
    private val mediaList: List<UiMedia>
) : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(mediaList, newText)
        return false
    }
}
