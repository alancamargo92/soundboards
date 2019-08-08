package com.ukdev.carcadasalborghetti.fragments

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.handlers.MediaHandler
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.listeners.QueryListener
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.ErrorType
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.hasStoragePermissions
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.utils.requestStoragePermissions
import com.ukdev.carcadasalborghetti.view.ViewLayer
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import kotlinx.android.synthetic.main.layout_list.*

abstract class MediaListFragment(private val mediaType: MediaType) : Fragment(),
        RecyclerViewInteractionListener,
        MediaCallback,
        ViewLayer {

    abstract val mediaHandler: MediaHandler
    abstract val adapter: MediaAdapter

    protected var media: List<Media> = listOf()

    private val viewModel by provideViewModel(MediaViewModel::class)
    private val layoutManager by lazy { GridLayoutManager(requireContext(), ITEM_SPAN) }

    private var searchView: SearchView? = null
    private var mediaToShare: Media? = null

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

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsGranted = grantResults.all { it == PERMISSION_GRANTED }

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSIONS == permissionsGranted)
            mediaToShare?.let { media ->
                mediaHandler.share(media, mediaType)
            }
    }

    override fun onItemClick(media: Media) {
        mediaHandler.play(media)
    }

    override fun onItemLongClick(media: Media) {
        if (SDK_INT >= M && !hasStoragePermissions()) {
            mediaToShare = media
            requestStoragePermissions(REQUEST_CODE_STORAGE_PERMISSIONS)
        } else {
            mediaHandler.share(media, mediaType)
        }
    }

    override fun displayMedia(media: LiveData<List<Media>>) {
        media.observe(this, Observer {
            this.media = it
            adapter.setData(it)
            searchView?.setOnQueryTextListener(QueryListener(adapter, it))
            hideProgressBar()
        })
    }

    override fun onError(errorType: ErrorType) {
        hideProgressBar()
        // TODO
    }

    private fun configureRecyclerView() {
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter.apply { setListener(this@MediaListFragment) }
    }

    private fun fetchMedia() {
        showProgressBar()
        viewModel.getMedia(mediaType, view = this)
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
        private const val REQUEST_CODE_STORAGE_PERMISSIONS = 123
    }

}