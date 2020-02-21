package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.handlers.MediaHandler
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.listeners.QueryListener
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.view.ViewLayer
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import kotlinx.android.synthetic.main.layout_list.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

abstract class MediaListFragment(
        @LayoutRes layoutId: Int,
        private val mediaType: MediaType
) : Fragment(layoutId),
        RecyclerViewInteractionListener,
        MediaCallback,
        ViewLayer {

    abstract val mediaHandler: MediaHandler
    abstract val adapter: MediaAdapter

    protected var media: List<Media> = listOf()

    private val viewModel by viewModel<MediaViewModel>()

    private val layoutManager by lazy {
        GridLayoutManager(requireContext(), ITEM_SPAN, RecyclerView.VERTICAL, false)
    }

    private var searchView: SearchView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        fetchMedia()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        menu.run {
            searchView = findItem(R.id.item_search)?.actionView as SearchView
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClick(media: Media) {
        lifecycleScope.launch {
            adapter.notifyItemClicked()
            mediaHandler.play(media)
            adapter.notifyItemReady()
        }
    }

    override fun onItemLongClick(media: Media) {
        mediaHandler.share(media, mediaType)
    }

    private fun displayMedia(media: List<Media>) {
        adapter.setData(media)
        searchView?.setOnQueryTextListener(QueryListener(adapter, media))
        hideProgressBar()
    }

    private fun onErrorFetchingData(errorType: ErrorType) {
        progress_bar.visibility = GONE
        recycler_view.visibility = GONE
        group_error.visibility = VISIBLE

        val icon: Int
        val text: Int

        if (errorType == ErrorType.CONNECTION) {
            icon = R.drawable.ic_disconnected
            text = R.string.error_connection
        } else {
            icon = R.drawable.ic_error
            text = R.string.error_unknown
        }

        img_error.setImageResource(icon)
        txt_error.setText(text)
        bt_try_again.setOnClickListener { fetchMedia() }
    }

    override fun onMediaError(errorType: ErrorType) {
        val msg = if (errorType == ErrorType.CONNECTION) {
            R.string.error_media_disconnected
        } else {
            R.string.error_media_unknown
        }
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    override fun notifyItemReady() {
        adapter.notifyItemReady()
    }

    private fun configureRecyclerView() {
        recycler_view.layoutManager = layoutManager
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter.apply { setListener(this@MediaListFragment) }
    }

    private fun fetchMedia() {
        group_error.visibility = GONE
        showProgressBar()
        lifecycleScope.launch {
            viewModel.getMedia(mediaType).observe(this@MediaListFragment, Observer { result ->
                when (result) {
                    is Success<List<Media>> -> displayMedia(result.body)
                    is GenericError -> onErrorFetchingData(ErrorType.UNKNOWN)
                    is NetworkError -> onErrorFetchingData(ErrorType.CONNECTION)
                }
            })
        }
    }

    private fun showProgressBar() {
        recycler_view.visibility = GONE
        progress_bar.visibility = VISIBLE
    }

    private fun hideProgressBar() {
        progress_bar.visibility = GONE
        recycler_view.visibility = VISIBLE
    }

    companion object {
        private const val ITEM_SPAN = 3
    }

}