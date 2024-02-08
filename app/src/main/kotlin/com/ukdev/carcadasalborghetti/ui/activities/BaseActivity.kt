package com.ukdev.carcadasalborghetti.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.data.tools.PreferencesHelper
import com.ukdev.carcadasalborghetti.databinding.ActivityBaseBinding
import com.ukdev.carcadasalborghetti.ui.adapter.PagerAdapter
import com.ukdev.carcadasalborghetti.ui.fragments.DefaultMediaListFragment
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragment
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    abstract val baseBinding: ActivityBaseBinding

    @Inject
    lateinit var menuProvider: MenuProvider

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private var currentFragment: MediaListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(baseBinding.toolbar)
        configureViewPager()

        if (preferencesHelper.shouldShowTip())
            showTip()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuProvider.getMenuItemsAndActions()[item.itemId]?.invoke(this)
        return true
    }

    private fun configureViewPager() {
        val labelsAndFragments = mapOf(
            R.string.audios to DefaultMediaListFragment.newInstance(MediaListFragmentType.AUDIO),
            R.string.videos to DefaultMediaListFragment.newInstance(MediaListFragmentType.VIDEO),
            R.string.favourites to DefaultMediaListFragment.newInstance(MediaListFragmentType.FAVOURITES)
        )
        val pagerAdapter = PagerAdapter(supportFragmentManager, labelsAndFragments)
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            if (fragment is MediaListFragment) {
                currentFragment = fragment
            }
        }

        with(baseBinding.viewPager) {
            adapter = pagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(baseBinding.tabLayout))
            baseBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    baseBinding.viewPager.currentItem = tab.position
                    val currentFragment = pagerAdapter.getItem(tab.position)
                    (currentFragment as? DefaultMediaListFragment)?.let {
                        this@BaseActivity.currentFragment = it
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    private fun showTip() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.tip_title)
            .setMessage(R.string.tip)
            .setNeutralButton(R.string.ok, null)
            .setPositiveButton(R.string.do_not_show_again) { _, _ ->
                preferencesHelper.disableTip()
            }.show()
    }
}
