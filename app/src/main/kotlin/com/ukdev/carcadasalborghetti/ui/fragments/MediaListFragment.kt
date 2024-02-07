package com.ukdev.carcadasalborghetti.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.extensions.observeFlow
import com.ukdev.carcadasalborghetti.databinding.LayoutListBinding
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.ui.media.MediaHandler
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListUiAction
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListUiState
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModel

private const val TAG_OPERATIONS_DIALOGUE = "operations-dialogue"

abstract class MediaListFragment(
    protected val mediaType: MediaTypeV2
) : Fragment() {

    abstract val mediaHandler: MediaHandler

    protected abstract val baseBinding: LayoutListBinding

    protected val viewModel by viewModels<MediaListViewModel>()

    private val adapter by lazy {
        MediaAdapter(
            onItemClicked = viewModel::onItemClicked,
            onItemLongClicked = viewModel::onItemLongClicked
        )
    }
    private var searchView: SearchView? = null

    protected abstract fun setStopButtonVisibility(isVisible: Boolean)

    protected abstract fun getMediaList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observeViewModelFlows()
        observePlaybackState()
        viewModel.getMediaList(mediaType)
        getMediaList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        searchView = (menu.findItem(R.id.item_search)?.actionView as SearchView).apply {
            queryHint = getString(R.string.search)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpUi() = with(baseBinding) {
        swipeRefreshLayout.setOnRefreshListener {
            baseBinding.swipeRefreshLayout.isRefreshing = true
            getMediaList()
        }

        btTryAgain.setOnClickListener {
            it.isVisible = false
            getMediaList()
        }

        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.black)
        recyclerView.adapter = adapter
        setHasOptionsMenu(true)
    }

    private fun observeViewModelFlows() {
        observeFlow(viewModel.state, ::onStateChanged)
        observeFlow(viewModel.action, ::onAction)
    }

    private fun onStateChanged(state: MediaListUiState) = with(state) {
        baseBinding.progressBar.isVisible = isLoading
        baseBinding.groupError.isVisible = error != null
        baseBinding.btTryAgain.isVisible = error != null && error != UiError.NO_FAVOURITES
        baseBinding.recyclerView.isVisible = mediaList != null
        setStopButtonVisibility(showStopButton)
        mediaList?.let(adapter::submitList)

        error?.let {
            baseBinding.imgError.setImageResource(it.iconRes)
            baseBinding.txtError.setText(it.textRes)
        }
    }

    private fun onAction(action: MediaListUiAction) {
        when (action) {
            is MediaListUiAction.PlayMedia -> TODO()
            is MediaListUiAction.StopPlayback -> TODO()
            is MediaListUiAction.ShareMedia -> TODO()
            is MediaListUiAction.ShowAvailableOperations -> showOperationsDialogue(
                action.operations
            )
        }
    }

    private fun observePlaybackState() {
        mediaHandler.isPlaying().observe(viewLifecycleOwner) { isPlaying ->
            setStopButtonVisibility(isPlaying)
        }
    }

    private fun showOperationsDialogue(operations: List<UiOperation>) {
        val dialogue = OperationsDialogue.newInstance(operations)
        dialogue.show(childFragmentManager, TAG_OPERATIONS_DIALOGUE)
    }
}
