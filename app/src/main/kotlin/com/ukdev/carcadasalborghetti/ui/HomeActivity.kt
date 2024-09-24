package com.ukdev.carcadasalborghetti.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.extensions.observeFlow
import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper
import com.ukdev.carcadasalborghetti.databinding.ActivityHomeBinding
import com.ukdev.carcadasalborghetti.ui.fragments.FragmentListProvider
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import com.ukdev.carcadasalborghetti.ui.viewmodel.home.HomeUiAction
import com.ukdev.carcadasalborghetti.ui.viewmodel.home.HomeViewModel
import javax.inject.Inject

abstract class HomeActivity : AppCompatActivity() {

    protected abstract val baseBinding: ActivityHomeBinding

    @Inject
    lateinit var fragmentListProvider: FragmentListProvider

    /*private val pagerAdapter by lazy {
        PagerAdapter(
            fragmentListProvider.provideFragmentList(),
            supportFragmentManager,
            lifecycle
        )
    }*/
    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var menuProvider: MenuProvider

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setUpUi()
        observeFlow(viewModel.action, ::onAction)
        viewModel.start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val context: Context = this
        menuProvider.getMenuItemsAndActions().find { (itemId, _) ->
            itemId == item.itemId
        }?.action?.invoke(context)
        return true
    }

    /*private fun setUpUi() {
        setSupportActionBar(baseBinding.toolbar)
        setUpViewPager()
    }*/

    /*private fun setUpViewPager() = with(baseBinding) {
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabInfo = MediaListTab.forPosition(position)
            tab.setIcon(tabInfo.iconRes)
            tab.setText(tabInfo.textRes)
        }.attach()
    }*/

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
