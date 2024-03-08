package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ukdev.carcadasalborghetti.core.extensions.args
import com.ukdev.carcadasalborghetti.core.extensions.putArguments
import com.ukdev.carcadasalborghetti.databinding.DialogueOperationsBinding
import com.ukdev.carcadasalborghetti.ui.adapter.OperationAdapter
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import com.ukdev.carcadasalborghetti.ui.viewmodel.medialist.MediaListViewModelAssistedFactory
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

    private val adapter by lazy {
        OperationAdapter { operation ->
            args.onOperationSelected(operation)
            dismiss()
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

    @Parcelize
    data class Args(
        val operations: List<UiOperation>,
        val media: UiMedia,
        val parentFragmentType: MediaListFragmentType,
        val onOperationSelected: (UiOperation) -> Unit
    ) : Parcelable

    companion object {

        fun newInstance(
            operations: List<UiOperation>,
            media: UiMedia,
            parentFragmentType: MediaListFragmentType,
            onOperationSelected: (UiOperation) -> Unit
        ): OperationsDialogue {
            val args = Args(operations, media, parentFragmentType, onOperationSelected)
            return OperationsDialogue().putArguments(args)
        }
    }
}
