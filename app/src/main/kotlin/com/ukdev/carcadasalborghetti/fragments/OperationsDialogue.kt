package com.ukdev.carcadasalborghetti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.OperationAdapter
import com.ukdev.carcadasalborghetti.model.Operation
import kotlinx.android.synthetic.main.dialogue_operations.*

class OperationsDialogue : DialogFragment(), OperationAdapter.ItemClickListener {

    @Suppress("UNCHECKED_CAST")
    private val operations by lazy {
        arguments?.getParcelableArray(KEY_OPERATIONS)
                ?.toList() as? List<Operation>
                ?: listOf(Operation.SHARE)
    }

    private val adapter = OperationAdapter(this)
    private var listener: OnOperationSelectedListener? = null

    fun setOnOperationSelectedListener(listener: OnOperationSelectedListener) {
        this.listener = listener
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialogue_operations, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        adapter.submitData(operations)
        
        bt_cancel.setOnClickListener {
            dismiss()
        }

        return view
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

    interface OnOperationSelectedListener {
        fun onOperationSelected(operation: Operation)
    }

}