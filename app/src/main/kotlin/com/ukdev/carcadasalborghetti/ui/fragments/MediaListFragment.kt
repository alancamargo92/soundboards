package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.data.entities.GenericError
import com.ukdev.carcadasalborghetti.data.entities.NetworkError
import com.ukdev.carcadasalborghetti.data.entities.Success
import com.ukdev.carcadasalborghetti.databinding.LayoutListBinding
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.ui.entities.ErrorType
import com.ukdev.carcadasalborghetti.ui.listeners.QueryListener
import com.ukdev.carcadasalborghetti.ui.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.ui.media.MediaHandler
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class MediaListFragment(private val mediaType: MediaType) : Fragment(),
    RecyclerViewInteractionListener,
    OperationsDialogue.Listener,
    DialogInterface.OnDismissListener,
    SwipeRefreshLayout.OnRefreshListener {

    abstract val mediaHandler: MediaHandler

    protected abstract val baseBinding: LayoutListBinding
    protected abstract val adapter: MediaAdapter

    private val viewModel by sharedViewModel<MediaViewModel>()

    private var searchView: SearchView? = null

    private lateinit var selectedMedia: Media

    abstract fun showFab()

    abstract fun hideFab()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSwipeRefreshLayout()
        configureRecyclerView()
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
            val operations = viewModel.getAvailableOperations(media)

            if (operations.isOnlyShare()) {
                mediaHandler.share(media)
                adapter.notifyItemReady()
            } else {
                selectedMedia = media
                showOperationsDialogue(operations)
            }
        }
    }

    override fun onOperationSelected(operation: Operation) {
        when (operation) {
            Operation.ADD_TO_FAVOURITES -> viewModel.saveToFavourites(selectedMedia)
            Operation.REMOVE_FROM_FAVOURITES -> viewModel.removeFromFavourites(selectedMedia)
            Operation.SHARE -> lifecycleScope.launch {
                mediaHandler.share(selectedMedia)
            }
        }

        adapter.notifyItemReady()
    }

    override fun onDismiss(dialogue: DialogInterface?) {
        adapter.notifyItemReady()
    }

    override fun onRefresh() {
        baseBinding.swipeRefreshLayout.isRefreshing = true
        fetchMedia(mediaType)
    }

    private fun configureSwipeRefreshLayout() = with(baseBinding.swipeRefreshLayout) {
        setOnRefreshListener(this@MediaListFragment)
        setColorSchemeResources(R.color.red, R.color.black)
    }

    private fun configureRecyclerView() {
        baseBinding.recyclerView.adapter = adapter.apply { setListener(this@MediaListFragment) }
    }

    private fun fetchMedia(mediaType: MediaType) {
        baseBinding.groupError.isVisible = false
        showProgressBar()

        if (mediaType == MediaType.BOTH) fetchFavourites()
        else fetchAudiosOrVideos()
    }

    private fun fetchFavourites() {
        lifecycleScope.launch {
            when (val result = viewModel.getFavourites()) {
                is Success<LiveData<List<Media>>> -> observeFavourites(result.body)
                is GenericError -> showError(ErrorType.UNKNOWN)
                is NetworkError -> showError(ErrorType.UNKNOWN)
            }
        }
    }

    private fun fetchAudiosOrVideos() {
        lifecycleScope.launch {
            viewModel.getMedia(mediaType).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Success<List<Media>> -> displayMedia(result.body)
                    is GenericError -> showError(ErrorType.UNKNOWN)
                    is NetworkError -> showError(ErrorType.CONNECTION)
                }
            }
        }
    }

    private fun observeFavourites(favouritesLiveData: LiveData<List<Media>>) {
        favouritesLiveData.observe(viewLifecycleOwner) { favourites ->
            displayMedia(favourites)
        }
    }

    private fun observePlaybackState() {
        mediaHandler.isPlaying().observe(viewLifecycleOwner) { isPlaying ->
            if (isPlaying)
                showFab()
            else
                hideFab()
        }
    }

    private fun showProgressBar() = with(baseBinding) {
        recyclerView.isVisible = false
        progressBar.isVisible = true
    }

    private fun displayMedia(media: List<Media>) {
        hideErrorIfVisible()

        if (media.isEmpty()) {
            showError(ErrorType.NO_FAVOURITES)
        } else {
            adapter.submitData(media)
            searchView?.setOnQueryTextListener(QueryListener(adapter, media))
            hideProgressBar()
        }
    }

    private fun hideErrorIfVisible() = with(baseBinding) {
        groupError.isVisible = false
        btTryAgain.isVisible = false
    }

    private fun showError(errorType: ErrorType) = with(baseBinding) {
        progressBar.isVisible = false
        recyclerView.isVisible = false
        groupError.isVisible = true

        if (errorType != ErrorType.NO_FAVOURITES) {
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

    private fun showOperationsDialogue(operations: List<Operation>) {
        OperationsDialogue.newInstance(operations).apply {
            setOnOperationSelectedListener(this@MediaListFragment)
        }.show(childFragmentManager, null)
    }

    private fun List<Operation>.isOnlyShare(): Boolean {
        return size == 1 && first() == Operation.SHARE
    }

}