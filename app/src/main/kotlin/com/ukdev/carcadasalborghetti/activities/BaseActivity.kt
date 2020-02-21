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
import com.ukdev.carcadasalborghetti.utils.PreferenceUtils
import com.ukdev.carcadasalborghetti.utils.getAppName
import com.ukdev.carcadasalborghetti.utils.getAppVersion
import com.ukdev.carcadasalborghetti.utils.getFragments
import kotlinx.android.synthetic.main.activity_base.*

open class BaseActivity : AppCompatActivity(R.layout.activity_main) {

    private val preferenceUtils by lazy { PreferenceUtils(this) }

    private lateinit var mediaHandler: MediaHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        configureTabLayout()
        configureViewPager()

        if (preferenceUtils.shouldShowTip())
            showTip()
    }

    override fun onBackPressed() {
        mediaHandler.stop()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_privacy -> showPrivacyPolicy()
            R.id.item_about -> showAppInfo()
            else -> false
        }
    }

    private fun configureTabLayout() {
        tab_layout.run {
            getFragments().forEach {
                addTab(newTab().setText(it.key))
            }
            tabGravity = TabLayout.GRAVITY_FILL
        }
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
                    if (mediaHandler.isPlaying())
                        mediaHandler.stop()
                    mediaHandler = (pagerAdapter.getItem(tab.position) as MediaListFragment).mediaHandler
                }

                override fun onTabUnselected(tab: TabLayout.Tab) { }

                override fun onTabReselected(tab: TabLayout.Tab) { }
            })
        }
    }

    private fun showPrivacyPolicy(): Boolean {
        AlertDialog.Builder(this).setView(R.layout.dialogue_privacy_terms)
                .setNeutralButton(R.string.ok, null)
                .show()
        return true
    }

    private fun showAppInfo(): Boolean {
        val title = getString(R.string.app_info, getAppName(), getAppVersion())
        AlertDialog.Builder(this).setTitle(title)
                .setMessage(R.string.developer_info)
                .setNeutralButton(R.string.ok, null)
                .setIcon(R.mipmap.ic_launcher)
                .show()
        return true
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