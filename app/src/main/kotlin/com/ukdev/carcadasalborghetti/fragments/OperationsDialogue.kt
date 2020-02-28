package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ukdev.carcadasalborghetti.model.Operation

class OperationsDialogue : DialogFragment() {

    @Suppress("UNCHECKED_CAST")
    private val operations by lazy {
        arguments?.getParcelableArray(KEY_OPERATIONS)
                ?.toList() as? List<Operation>
                ?: listOf(Operation.SHARE)
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

    interface OperationSelectedListener {
        fun onOperationSelected(operation: Operation)
    }

}