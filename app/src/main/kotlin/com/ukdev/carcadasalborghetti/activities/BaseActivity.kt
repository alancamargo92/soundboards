package com.ukdev.carcadasalborghetti.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.PagerAdapter
import com.ukdev.carcadasalborghetti.fragments.MediaListFragment
import com.ukdev.carcadasalborghetti.handlers.MediaHandler
import com.ukdev.carcadasalborghetti.utils.MenuProvider
import com.ukdev.carcadasalborghetti.utils.PreferenceUtils
import kotlinx.android.synthetic.main.activity_base.*
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity(R.layout.activity_main) {

    private val menuProvider by inject<MenuProvider>()

    private val preferenceUtils by lazy { PreferenceUtils(this) }

    private lateinit var mediaHandler: MediaHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        configureViewPager()

        if (preferenceUtils.shouldShowTip())
            showTip()
    }

    override fun onBackPressed() {
        mediaHandler.stop()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId?.let {
            menuProvider.getMenuItemsAndActions()[it]?.invoke()
        }
        return true
    }

    private fun configureViewPager() {
        val pagerAdapter = PagerAdapter(supportFragmentManager, tab_layout.tabCount)
        mediaHandler = (pagerAdapter.getItem(0) as MediaListFragment).mediaHandler
        view_pager.run {
            adapter = pagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
            tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    view_pager.currentItem = tab.position
                    mediaHandler.stop()
                    mediaHandler = (pagerAdapter.getItem(tab.position) as MediaListFragment).mediaHandler
                }

                override fun onTabUnselected(tab: TabLayout.Tab) { }

                override fun onTabReselected(tab: TabLayout.Tab) { }
            })
        }
    }

    private fun showTip() {
        AlertDialog.Builder(this)
                .setTitle(R.string.tip_title)
                .setMessage(R.string.tip)
                .setNeutralButton(R.string.ok, null)
                .setPositiveButton(R.string.do_not_show_again) { _, _ ->
                    preferenceUtils.disableTip()
                }.show()
    }

}