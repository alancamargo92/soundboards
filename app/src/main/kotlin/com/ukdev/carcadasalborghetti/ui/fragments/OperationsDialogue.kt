package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ukdev.carcadasalborghetti.core.extensions.args
import com.ukdev.carcadasalborghetti.core.extensions.putArguments
import com.ukdev.carcadasalborghetti.databinding.DialogueOperationsBinding
import com.ukdev.carcadasalborghetti.ui.adapter.OperationAdapter
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModel
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModelAssistedFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class OperationsDialogue : DialogFragment() {

    private var _binding: DialogueOperationsBinding? = null
    private val binding: DialogueOperationsBinding
        get() = _binding!!

    @Inject
    lateinit var assistedFactory: MediaListViewModelAssistedFactory

    private val args by args<Args>()
    private val activityViewModel by activityViewModels<MediaListViewModel> {
        MediaListViewModel.Factory(assistedFactory, args.parentFragmentType)
    }

    private val adapter by lazy {
        OperationAdapter { operation ->
            onOperationSelected(operation, args.media)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogueOperationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        adapter.submitList(args.operations)
    }

    override fun onDismiss(dialogue: DialogInterface) {
        val parent = requireParentFragment()

        if (parent is DialogInterface.OnDismissListener) {
            parent.onDismiss(dialogue)
        }

        super.onDismiss(dialogue)
    }

    private fun onOperationSelected(operation: UiOperation, media: UiMedia) {
        activityViewModel.onOperationSelected(operation, media)
        dismiss()
    }

    @Parcelize
    data class Args(
        val operations: List<UiOperation>,
        val media: UiMedia,
        val parentFragmentType: MediaListFragmentType
    ) : Parcelable

    companion object {
        fun newInstance(
            operations: List<UiOperation>,
            media: UiMedia,
            parentFragmentType: MediaListFragmentType
        ): OperationsDialogue {
            val args = Args(operations, media, parentFragmentType)
            return OperationsDialogue().putArguments(args)
        }
    }
}
