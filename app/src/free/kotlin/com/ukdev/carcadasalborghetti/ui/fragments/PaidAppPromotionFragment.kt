package com.ukdev.carcadasalborghetti.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.extensions.observeFlow
import com.ukdev.carcadasalborghetti.ui.view.PaidAppPromotionScreen
import com.ukdev.carcadasalborghetti.ui.viewmodel.PaidAppPromotionUiAction
import com.ukdev.carcadasalborghetti.ui.viewmodel.PaidAppPromotionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaidAppPromotionFragment : MediaListFragment() {

    private val viewModel by viewModels<PaidAppPromotionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PaidAppPromotionScreen(viewModel::onGetPaidVersionClicked)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFlow(viewModel.action, ::onAction)
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return false
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    private fun onAction(action: PaidAppPromotionUiAction) = when (action) {
        is PaidAppPromotionUiAction.OpenWebPage -> openWebPage(action.uri)
    }

    private fun openWebPage(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}
