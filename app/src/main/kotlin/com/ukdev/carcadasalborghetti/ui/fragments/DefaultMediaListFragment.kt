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
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.extensions.args
import com.ukdev.carcadasalborghetti.core.extensions.observeFlow
import com.ukdev.carcadasalborghetti.core.extensions.orFalse
import com.ukdev.carcadasalborghetti.core.extensions.putArguments
import com.ukdev.carcadasalborghetti.databinding.LayoutListBinding
import com.ukdev.carcadasalborghetti.ui.VideoActivity
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.ui.adapter.QueryListener
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListUiAction
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListUiState
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModel
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModelAssistedFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

private const val TAG_OPERATIONS_DIALOGUE = "operations-dialogue"

@AndroidEntryPoint
class DefaultMediaListFragment : MediaListFragment() {

    private var _binding: LayoutListBinding? = null
    private val binding: LayoutListBinding
        get() = _binding!!

    @Inject
    lateinit var assistedFactory: MediaListViewModelAssistedFactory

    private val args by args<Args>()
    private val viewModel by viewModels<MediaListViewModel> {
        MediaListViewModel.Factory(assistedFactory, args.fragmentType)
    }

    private val adapter by lazy {
        MediaAdapter(
            onItemClicked = viewModel::onItemClicked,
            onItemLongClicked = viewModel::onItemLongClicked
        )
    }

    private var searchView: SearchView? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observeViewModelFlows()
        viewModel.getMediaList(isRefreshing = false)
    }

    override fun onStop() {
        binding.btStop.isVisible = false
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

    private fun setUpUi() = with(binding) {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getMediaList(isRefreshing = true)
        }

        btStop.setOnClickListener { viewModel.onStopButtonClicked() }

        btTryAgain.setOnClickListener {
            viewModel.getMediaList(isRefreshing = false)
        }

        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.black)
        recyclerView.adapter = adapter
        setHasOptionsMenu(true)
    }

    private fun observeViewModelFlows() {
        observeFlow(viewModel.state, ::onStateChanged)
        observeFlow(viewModel.action, ::onAction)
    }

    private fun onStateChanged(state: MediaListUiState) = with(binding) {
        progressBar.isVisible = state.isLoading
        swipeRefreshLayout.isRefreshing = state.isRefreshing
        groupError.isVisible = state.error != null
        btTryAgain.isVisible = state.error != null && state.error != UiError.NO_FAVOURITES
        recyclerView.isVisible = state.mediaList != null
        btStop.isVisible = state.showStopButton

        state.mediaList?.let {
            val listener = QueryListener(adapter, it)
            searchView?.setOnQueryTextListener(listener)
            adapter.submitList(it)
        }

        state.error?.let {
            imgError.setImageResource(it.iconRes)
            txtError.setText(it.textRes)
        }
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
        val dialogue = OperationsDialogue.newInstance(operations, media, args.fragmentType)
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
