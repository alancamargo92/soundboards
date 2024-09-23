package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.extensions.args
import com.ukdev.carcadasalborghetti.core.extensions.observeFlow
import com.ukdev.carcadasalborghetti.core.extensions.orFalse
import com.ukdev.carcadasalborghetti.core.extensions.putArguments
import com.ukdev.carcadasalborghetti.ui.VideoActivity
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import com.ukdev.carcadasalborghetti.ui.view.MediaListScreen
import com.ukdev.carcadasalborghetti.ui.viewmodel.medialist.MediaListUiAction
import com.ukdev.carcadasalborghetti.ui.viewmodel.medialist.MediaListUiState
import com.ukdev.carcadasalborghetti.ui.viewmodel.medialist.MediaListViewModel
import com.ukdev.carcadasalborghetti.ui.viewmodel.medialist.MediaListViewModelAssistedFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

private const val TAG_OPERATIONS_DIALOGUE = "operations-dialogue"

@AndroidEntryPoint
class DefaultMediaListFragment : MediaListFragment() {

    @Inject
    lateinit var assistedFactory: MediaListViewModelAssistedFactory

    private val args by args<Args>()
    private val viewModel by viewModels<MediaListViewModel> {
        MediaListViewModel.Factory(assistedFactory, args.fragmentType)
    }

    private val itemsState = mutableStateOf(emptyList<UiMedia>())
    private val showStopButtonState = mutableStateOf(false)
    private val showProgressBarState = mutableStateOf(false)
    private val errorState = mutableStateOf<UiError?>(null)

    private var searchView: SearchView? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MediaListScreen(
                    items = itemsState.value,
                    onItemClicked = viewModel::onItemClicked,
                    onItemLongClicked = viewModel::onItemLongClicked,
                    showStopButton = showStopButtonState.value,
                    onFabClicked = viewModel::onStopButtonClicked,
                    showProgressBar = showProgressBarState.value,
                    error = errorState.value,
                    onTryAgainClicked = { viewModel.getMediaList(isRefreshing = false) }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpUi()
        setHasOptionsMenu(true)
        observeViewModelFlows()
        viewModel.getMediaList(isRefreshing = false)
    }

    override fun onStop() {
        showStopButtonState.value = false
        stopAudioPlayback()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        searchView = (menu.findItem(R.id.item_search)?.actionView as SearchView).apply {
            queryHint = getString(R.string.search)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    /*private fun setUpUi() = with(binding) {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getMediaList(isRefreshing = true)
        }

        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.black)
        setHasOptionsMenu(true)
    }*/

    private fun observeViewModelFlows() {
        observeFlow(viewModel.state, ::onStateChanged)
        observeFlow(viewModel.action, ::onAction)
    }

    private fun onStateChanged(state: MediaListUiState) {
        itemsState.value = state.mediaList.orEmpty()
        showStopButtonState.value = state.showStopButton
        showProgressBarState.value = state.isLoading
        errorState.value = state.error
        /*swipeRefreshLayout.isRefreshing = state.isRefreshing

        state.mediaList?.let {
            val listener = QueryListener(adapter, it)
            searchView?.setOnQueryTextListener(listener)
            adapter.submitList(it)
        }*/
    }

    private fun onAction(action: MediaListUiAction) {
        when (action) {
            is MediaListUiAction.PlayAudio -> playAudio(action.media)

            is MediaListUiAction.PlayVideo -> playVideo(action.media)

            is MediaListUiAction.StopAudioPlayback -> stopAudioPlayback()

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

    private fun playAudio(media: UiMedia) {
        mediaPlayer?.release()
        mediaPlayer = createMediaPlayer(media.uri)
    }

    private fun playVideo(media: UiMedia) {
        val intent = VideoActivity.getIntent(context = requireContext(), media = media)
        startActivity(intent)
    }

    private fun stopAudioPlayback() {
        mediaPlayer?.stop()
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
        val dialogue = OperationsDialogue.newInstance(operations, media, args.fragmentType) {
            viewModel.onOperationSelected(it, media)
        }
        dialogue.show(childFragmentManager, TAG_OPERATIONS_DIALOGUE)
    }

    private fun createMediaPlayer(uri: Uri): MediaPlayer? {
        return MediaPlayer.create(requireContext(), uri)?.apply {
            if (isPlaying.orFalse()) {
                stop()
                start()
            } else {
                start()
            }

            setOnCompletionListener { viewModel.onPlaybackCompleted() }
        }
    }

    @Parcelize
    data class Args(val fragmentType: MediaListFragmentType) : Parcelable

    companion object {

        fun newInstance(fragmentType: MediaListFragmentType): DefaultMediaListFragment {
            val args = Args(fragmentType)
            return DefaultMediaListFragment().putArguments(args)
        }
    }
}
