package com.ukdev.carcadasalborghetti.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper
import com.ukdev.carcadasalborghetti.core.tools.PreferencesHelper
import com.ukdev.carcadasalborghetti.databinding.ActivityMainBinding
import com.ukdev.carcadasalborghetti.ui.adapter.PagerAdapter
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragment
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragmentMapProvider
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import javax.inject.Inject

abstract class MainActivity : AppCompatActivity() {

    protected abstract val baseBinding: ActivityMainBinding

    @Inject
    lateinit var menuProvider: MenuProvider

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    @Inject
    lateinit var fragmentMapProvider: MediaListFragmentMapProvider

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    private var currentFragment: MediaListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(baseBinding.toolbar)
        configureViewPager()

        if (preferencesHelper.shouldShowTip()) {
            showTip()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val context: Context = this
        menuProvider.getMenuItemsAndActions().find { (itemId, _) ->
            itemId == item.itemId
        }?.action?.invoke(context)
        return true
    }

    private fun configureViewPager() {
        val fragmentMap = fragmentMapProvider.provideFragmentMap()
        val pagerAdapter = PagerAdapter(supportFragmentManager, fragmentMap)
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

    private fun showTip() {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R.string.tip_title,
            messageRes = R.string.tip,
            positiveButtonTextRes = R.string.do_not_show_again,
            onPositiveButtonClicked = preferencesHelper::disableTip
        )
    }
}
