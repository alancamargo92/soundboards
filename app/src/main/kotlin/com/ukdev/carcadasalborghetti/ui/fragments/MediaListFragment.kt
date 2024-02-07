package com.ukdev.carcadasalborghetti.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.LayoutListBinding
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.ui.listeners.QueryListener
import com.ukdev.carcadasalborghetti.ui.media.MediaHandler
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModel
import kotlinx.coroutines.launch

abstract class MediaListFragment(private val mediaType: MediaTypeV2) : Fragment(),
    SwipeRefreshLayout.OnRefreshListener {

    abstract val mediaHandler: MediaHandler

    protected abstract val baseBinding: LayoutListBinding

    private val adapter by lazy {
        MediaAdapter(
            onItemClicked = viewModel::onItemClicked,
            onItemLongClicked = viewModel::onItemLongClicked
        )
    }

    private val viewModel by viewModels<MediaListViewModel>()
    private var searchView: SearchView? = null

    protected abstract fun setStopButtonVisibility(isVisible: Boolean)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        fetchMedia(mediaType)
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

    override fun onRefresh() {
        baseBinding.swipeRefreshLayout.isRefreshing = true
        fetchMedia(mediaType)
    }

    private fun setUpUi() = with(baseBinding) {
        swipeRefreshLayout.setOnRefreshListener(this@MediaListFragment)
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.black)
        recyclerView.adapter = adapter
    }

    private fun fetchMedia(mediaType: MediaTypeV2) {
        baseBinding.groupError.isVisible = false
        showProgressBar()

        if (mediaType == MediaType.BOTH) fetchFavourites()
        else fetchAudiosOrVideos()
    }

    private fun fetchFavourites() {
        lifecycleScope.launch {
            when (val result = viewModel.getFavourites()) {
                is Success<LiveData<List<Media>>> -> observeFavourites(result.body)
                is GenericError -> showError(UiError.UNKNOWN)
                is NetworkError -> showError(UiError.UNKNOWN)
            }
        }
    }

    private fun fetchAudiosOrVideos() {
        lifecycleScope.launch {
            viewModel.getMedia(mediaType).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Success<List<Media>> -> displayMedia(result.body)
                    is GenericError -> showError(UiError.UNKNOWN)
                    is NetworkError -> showError(UiError.CONNECTION)
                }
            }
        }
    }

    private fun observeFavourites(favouritesLiveData: LiveData<List<MediaV2>>) {
        favouritesLiveData.observe(viewLifecycleOwner) { favourites ->
            displayMedia(favourites)
        }
    }

    private fun observePlaybackState() {
        mediaHandler.isPlaying().observe(viewLifecycleOwner) { isPlaying ->
            setStopButtonVisibility(isPlaying)
        }
    }

    private fun showProgressBar() = with(baseBinding) {
        recyclerView.isVisible = false
        progressBar.isVisible = true
    }

    private fun displayMedia(mediaList: List<MediaV2>) {
        hideErrorIfVisible()

        if (mediaList.isEmpty()) {
            showError(UiError.NO_FAVOURITES)
        } else {
            adapter.submitList(mediaList)
            searchView?.setOnQueryTextListener(QueryListener(adapter, mediaList))
            hideProgressBar()
        }
    }

    private fun hideErrorIfVisible() = with(baseBinding) {
        groupError.isVisible = false
        btTryAgain.isVisible = false
    }

    private fun showError(errorType: UiError) = with(baseBinding) {
        progressBar.isVisible = false
        recyclerView.isVisible = false
        groupError.isVisible = true

        if (errorType != UiError.NO_FAVOURITES) {
            btTryAgain.isVisible = true
            btTryAgain.setOnClickListener {
                it.isVisible = false
                fetchAudiosOrVideos()
            }
        }

        val icon = errorType.iconRes
        val text = errorType.textRes

        imgError.setImageResource(icon)
        txtError.setText(text)
    }

    private fun hideProgressBar() = with(baseBinding) {
        progressBar.isVisible = false
        recyclerView.isVisible = true
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showOperationsDialogue(operations: List<UiOperation>) {
        OperationsDialogue.newInstance(operations).show(childFragmentManager, null)
    }
}
