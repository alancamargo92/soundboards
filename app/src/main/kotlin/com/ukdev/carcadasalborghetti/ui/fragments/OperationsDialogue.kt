package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ukdev.carcadasalborghetti.databinding.DialogueOperationsBinding
import com.ukdev.carcadasalborghetti.ui.adapter.OperationAdapter
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import com.ukdev.carcadasalborghetti.core.extensions.args
import com.ukdev.carcadasalborghetti.core.extensions.putArguments
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModel
import kotlinx.parcelize.Parcelize

class OperationsDialogue : DialogFragment() {

    private var _binding: DialogueOperationsBinding? = null
    private val binding: DialogueOperationsBinding
        get() = _binding!!

    private val args by args<Args>()
    private val adapter by lazy { OperationAdapter(::onOperationSelected) }
    private val activityViewModel by viewModels<MediaListViewModel>()

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

    private fun onOperationSelected(operation: UiOperation) {
        activityViewModel.onOperationSelected(operation)
        dismiss()
    }

    @Parcelize
    data class Args(val operations: List<UiOperation>) : Parcelable

    companion object {
        fun newInstance(operations: List<UiOperation>): OperationsDialogue {
            val args = Args(operations)
            return OperationsDialogue().putArguments(args)
        }
    }
}
