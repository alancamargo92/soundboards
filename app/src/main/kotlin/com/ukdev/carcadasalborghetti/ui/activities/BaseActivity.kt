package com.ukdev.carcadasalborghetti.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.adapter.PagerAdapter
import com.ukdev.carcadasalborghetti.framework.media.MediaHandler
import com.ukdev.carcadasalborghetti.data.tools.PreferencesHelper
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragment
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import kotlinx.android.synthetic.main.activity_base.*
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity(R.layout.activity_main) {

    private val menuProvider by inject<MenuProvider>()
    private val preferencesHelper by inject<PreferencesHelper>()

    private lateinit var mediaHandler: MediaHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        configureViewPager()

        if (preferencesHelper.shouldShowTip())
            showTip()
    }

    override fun onBackPressed() {
        mediaHandler.stop()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuProvider.getMenuItemsAndActions()[item.itemId]?.invoke(this)
        return true
    }

    private fun configureViewPager() {
        val pagerAdapter = PagerAdapter(supportFragmentManager, tab_layout.tabCount)
        mediaHandler = (pagerAdapter.getItem(0) as MediaListFragment).mediaHandler
        with(view_pager) {
            adapter = pagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
            tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    view_pager.currentItem = tab.position
                    val currentFragment = pagerAdapter.getItem(tab.position)
                    mediaHandler.stop()
                    mediaHandler = (currentFragment as MediaListFragment).mediaHandler
                }

                override fun onTabUnselected(tab: TabLayout.Tab) { }

                override fun onTabReselected(tab: TabLayout.Tab) { }
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