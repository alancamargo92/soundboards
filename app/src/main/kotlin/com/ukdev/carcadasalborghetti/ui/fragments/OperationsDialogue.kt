package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ukdev.carcadasalborghetti.databinding.DialogueOperationsBinding
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import com.ukdev.carcadasalborghetti.ui.adapter.OperationAdapter

class OperationsDialogue : DialogFragment(), OperationAdapter.ItemClickListener {

    private var _binding: DialogueOperationsBinding? = null
    private val binding: DialogueOperationsBinding
        get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    private val operations by lazy {
        arguments?.getParcelableArray(KEY_OPERATIONS)
            ?.toList() as? List<Operation>
            ?: listOf(Operation.SHARE)
    }

    private val adapter = OperationAdapter(listener = this)
    private var listener: Listener? = null

    fun setOnOperationSelectedListener(listener: Listener) {
        this.listener = listener
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
        adapter.submitData(operations)
    }

    override fun onDismiss(dialogue: DialogInterface) {
        val parent = requireParentFragment()

        if (parent is DialogInterface.OnDismissListener)
            parent.onDismiss(dialogue)

        super.onDismiss(dialogue)
    }

    override fun onItemClick(operation: Operation) {
        listener?.onOperationSelected(operation)
        dismiss()
    }

    companion object {
        private const val KEY_OPERATIONS = "operations"

        fun newInstance(operations: List<Operation>): OperationsDialogue {
            val args = Bundle().apply {
                putParcelableArray(KEY_OPERATIONS, operations.toTypedArray())
            }

            return OperationsDialogue().apply {
                arguments = args
            }
        }
    }

    interface Listener {
        fun onOperationSelected(operation: Operation)
    }
}
