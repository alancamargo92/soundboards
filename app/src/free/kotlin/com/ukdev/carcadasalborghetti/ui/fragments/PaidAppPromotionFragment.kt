package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.extensions.observeFlow
import com.ukdev.carcadasalborghetti.databinding.FragmentPaidAppPromotionBinding
import com.ukdev.carcadasalborghetti.ui.viewmodel.PaidAppPromotionUiAction
import com.ukdev.carcadasalborghetti.ui.viewmodel.PaidAppPromotionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaidAppPromotionFragment : MediaListFragment() {

    private var _binding: FragmentPaidAppPromotionBinding? = null
    private val binding: FragmentPaidAppPromotionBinding
        get() = _binding!!

    private val viewModel by viewModels<PaidAppPromotionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaidAppPromotionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFlow(viewModel.action, ::onAction)
        setUpUi()
    }

    private fun setUpUi() = with(binding) {
        setHasOptionsMenu(true)
        txtPaidVersion.text = getString(R.string.get_paid_version_rationale)
        btPaidVersion.setOnClickListener {
            viewModel.onGetPaidVersionClicked()
        }
    }

    private fun onAction(action: PaidAppPromotionUiAction) = when (action) {
        is PaidAppPromotionUiAction.OpenWebPage -> openWebPage(action.uri)
    }

    private fun openWebPage(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}
