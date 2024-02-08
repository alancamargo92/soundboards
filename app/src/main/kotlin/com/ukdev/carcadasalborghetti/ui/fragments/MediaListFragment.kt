package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.Intent
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
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListUiAction
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListUiState
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModel

private const val TAG_OPERATIONS_DIALOGUE = "operations-dialogue"

abstract class MediaListFragment(
    protected val mediaType: MediaTypeV2
) : Fragment() {

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

    protected abstract fun getMediaList(isRefreshing: Boolean)

    protected abstract fun playMedia(media: UiMedia)

    protected abstract fun stopPlayback()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observeViewModelFlows()
        getMediaList(isRefreshing = false)
    }

    override fun onPause() {
        stopPlayback()
        super.onPause()
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
            getMediaList(isRefreshing = true)
        }

        btTryAgain.setOnClickListener {
            getMediaList(isRefreshing = false)
        }

        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.black)
        recyclerView.adapter = adapter
        setHasOptionsMenu(true)
    }

    private fun observeViewModelFlows() {
        observeFlow(viewModel.state, ::onStateChanged)
        observeFlow(viewModel.action, ::onAction)
    }

    private fun onStateChanged(state: MediaListUiState) = with(baseBinding) {
        progressBar.isVisible = state.isLoading
        swipeRefreshLayout.isRefreshing = state.isRefreshing
        groupError.isVisible = state.error != null
        btTryAgain.isVisible = state.error != null && state.error != UiError.NO_FAVOURITES
        recyclerView.isVisible = state.mediaList != null
        setStopButtonVisibility(state.showStopButton)
        state.mediaList?.let(adapter::submitList)

        state.error?.let {
            baseBinding.imgError.setImageResource(it.iconRes)
            baseBinding.txtError.setText(it.textRes)
        }
    }

    private fun onAction(action: MediaListUiAction) {
        when (action) {
            is MediaListUiAction.PlayMedia -> playMedia(action.media)

            is MediaListUiAction.StopPlayback -> stopPlayback()

            is MediaListUiAction.ShareMedia -> shareMedia(
                chooserTitleRes = action.chooserTitleRes,
                chooserSubjectRes = action.chooserSubjectRes,
                chooserType = action.chooserType,
                media = action.media
            )

            is MediaListUiAction.ShowAvailableOperations -> showOperationsDialogue(
                action.operations,
                action.media
            )
        }
    }

    private fun shareMedia(
        chooserTitleRes: Int,
        chooserSubjectRes: Int,
        chooserType: String,
        media: UiMedia
    ) {
        val shareIntent = Intent(Intent.ACTION_SEND).setType(chooserType)
            .putExtra(Intent.EXTRA_STREAM, media.uri)
            .putExtra(Intent.EXTRA_SUBJECT, getString(chooserSubjectRes))

        val chooser = Intent.createChooser(
            shareIntent,
            getString(chooserTitleRes)
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(chooser)
    }

    private fun showOperationsDialogue(operations: List<UiOperation>, media: UiMedia) {
        val dialogue = OperationsDialogue.newInstance(operations, media)
        dialogue.show(childFragmentManager, TAG_OPERATIONS_DIALOGUE)
    }
}
