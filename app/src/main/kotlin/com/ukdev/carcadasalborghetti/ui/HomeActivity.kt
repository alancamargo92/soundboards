package com.ukdev.carcadasalborghetti.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.extensions.observeFlow
import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper
import com.ukdev.carcadasalborghetti.databinding.ActivityHomeBinding
import com.ukdev.carcadasalborghetti.ui.adapter.PagerAdapter
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragment
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import com.ukdev.carcadasalborghetti.ui.viewmodel.home.HomeUiAction
import com.ukdev.carcadasalborghetti.ui.viewmodel.home.HomeUiState
import com.ukdev.carcadasalborghetti.ui.viewmodel.home.HomeViewModel
import javax.inject.Inject

abstract class HomeActivity : AppCompatActivity() {

    protected abstract val baseBinding: ActivityHomeBinding

    private val pagerAdapter by lazy { PagerAdapter(supportFragmentManager) }
    private val viewModel by viewModels<HomeViewModel>()

    private var currentFragment: MediaListFragment? = null

    @Inject
    lateinit var menuProvider: MenuProvider

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUi()
        observeViewModelFlows()
        viewModel.start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val context: Context = this
        menuProvider.getMenuItemsAndActions().find { (itemId, _) ->
            itemId == item.itemId
        }?.action?.invoke(context)
        return true
    }

    private fun setUpUi() {
        setSupportActionBar(baseBinding.toolbar)
        setUpViewPager()
    }

    private fun observeViewModelFlows() {
        observeFlow(viewModel.state, ::onStateChanged)
        observeFlow(viewModel.action, ::onAction)
    }

    private fun setUpViewPager() {
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            if (fragment is MediaListFragment) {
                currentFragment = fragment
            }
        }

        with(baseBinding.viewPager) {
            adapter = pagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(baseBinding.tabLayout))
            baseBinding.tabLayout.addOnTabSelectedListener(
                object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        baseBinding.viewPager.currentItem = tab.position
                        val fragment = pagerAdapter.getItem(tab.position)
                        if (fragment is MediaListFragment) {
                            currentFragment = fragment
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {}

                    override fun onTabReselected(tab: TabLayout.Tab) {}
                }
            )
        }
    }

    private fun onStateChanged(state: HomeUiState) {
        state.fragmentMap?.let(pagerAdapter::submitFragmentMap)
    }

    private fun onAction(action: HomeUiAction) = when (action) {
        HomeUiAction.ShowTip -> showTip()
    }

    private fun showTip() {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R.string.tip_title,
            messageRes = R.string.tip,
            positiveButtonTextRes = R.string.do_not_show_again,
            onPositiveButtonClicked = viewModel::onDoNotShowTipAgainClicked
        )
    }
}
