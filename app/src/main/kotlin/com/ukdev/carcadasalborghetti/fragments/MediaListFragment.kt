package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.handlers.MediaHandler
import com.ukdev.carcadasalborghetti.listeners.QueryListener
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.utils.hide
import com.ukdev.carcadasalborghetti.utils.isVisible
import com.ukdev.carcadasalborghetti.utils.show
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import kotlinx.android.synthetic.main.layout_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

abstract class MediaListFragment(
        @LayoutRes layoutId: Int,
        private val mediaType: MediaType
) : Fragment(layoutId), RecyclerViewInteractionListener {

    abstract val mediaHandler: MediaHandler

    protected abstract val adapter: MediaAdapter

    private val viewModel by viewModel<MediaViewModel>()

    private var searchView: SearchView? = null

    abstract fun showFab()

    abstract fun hideFab()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        fetchMedia()
        setHasOptionsMenu(true)
        observePlaybackState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        searchView = (menu.findItem(R.id.item_search)?.actionView as SearchView).apply {
            queryHint = getString(R.string.search)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClick(media: Media) {
        lifecycleScope.launch {
            adapter.notifyItemClicked()
            withContext(Dispatchers.IO) {
                mediaHandler.play(media)
            }
            adapter.notifyItemReady()
        }
    }

    override fun onItemLongClick(media: Media) {
        lifecycleScope.launch {
            mediaHandler.share(media)
            adapter.notifyItemReady()
        }
    }

    private fun configureRecyclerView() {
        recycler_view.adapter = adapter.apply { setListener(this@MediaListFragment) }
    }

    private fun fetchMedia() {
        group_error.hide()
        showProgressBar()
        lifecycleScope.launch {
            viewModel.getMedia(mediaType).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Success<List<Media>> -> displayMedia(result.body)
                    is GenericError -> showError(ErrorType.UNKNOWN)
                    is NetworkError -> showError(ErrorType.CONNECTION)
                }
            })
        }
    }

    private fun observePlaybackState() {
        mediaHandler.isPlaying().observe(viewLifecycleOwner, Observer { isPlaying ->
            if (isPlaying)
                showFab()
            else
                hideFab()
        })
    }

    private fun showProgressBar() {
        recycler_view.hide()
        progress_bar.show()
    }

    private fun displayMedia(media: List<Media>) {
        if (group_error.isVisible())
            group_error.hide()

        if (media.isEmpty()) {
            showError(ErrorType.NO_FAVOURITES)
            bt_try_again.hide()
        } else {
            adapter.submitData(media)
            searchView?.setOnQueryTextListener(QueryListener(adapter, media))
            hideProgressBar()
        }
    }

    private fun showError(errorType: ErrorType) {
        progress_bar.hide()
        recycler_view.hide()
        group_error.show()

        val icon = errorType.iconRes
        val text = errorType.textRes

        img_error.setImageResource(icon)
        txt_error.setText(text)
        bt_try_again.setOnClickListener { fetchMedia() }
    }

    private fun hideProgressBar() {
        progress_bar.hide()
        recycler_view.show()
    }

}