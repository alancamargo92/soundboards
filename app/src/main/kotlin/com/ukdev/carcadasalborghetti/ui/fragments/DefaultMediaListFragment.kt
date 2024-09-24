package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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
    private val isRefreshingState = mutableStateOf(false)

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
                    onTryAgainClicked = { viewModel.getMediaList(isRefreshing = false) },
                    onRefresh = { viewModel.getMediaList(isRefreshing = true) },
                    isRefreshing = isRefreshingState.value
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMenu()
        observeViewModelFlows()
        viewModel.getMediaList(isRefreshing = false)
    }

    private fun setUpMenu() {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                    searchView = (menu.findItem(R.id.item_search)?.actionView as SearchView).apply {
                        queryHint = getString(R.string.search)
                        setOnQueryTextListener(
                            object : SearchView.OnQueryTextListener {
                                override fun onQueryTextChange(newText: String?): Boolean {
                                    viewModel.searchMedia(newText)
                                    return false
                                }

                                override fun onQueryTextSubmit(query: String?): Boolean {
                                    return true
                                }
                            }
                        )
                    }
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return false
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    override fun onStop() {
        showStopButtonState.value = false
        stopAudioPlayback()
        super.onStop()
    }

    private fun observeViewModelFlows() {
        observeFlow(viewModel.state, ::onStateChanged)
        observeFlow(viewModel.action, ::onAction)
    }

    private fun onStateChanged(state: MediaListUiState) {
        itemsState.value = state.mediaList.orEmpty()
        showStopButtonState.value = state.showStopButton
        showProgressBarState.value = state.isLoading
        errorState.value = state.error
        isRefreshingState.value = state.isRefreshing
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
