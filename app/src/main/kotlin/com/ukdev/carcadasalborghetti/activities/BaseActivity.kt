package com.ukdev.carcadasalborghetti.activities

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.material.tabs.TabLayout
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.PagerAdapter
import com.ukdev.carcadasalborghetti.listeners.DeviceInteractionListener
import com.ukdev.carcadasalborghetti.utils.PreferenceUtils
import com.ukdev.carcadasalborghetti.utils.getAppName
import com.ukdev.carcadasalborghetti.utils.getAppVersion
import kotlinx.android.synthetic.main.activity_base.*

open class BaseActivity : AppCompatActivity() {

    private val preferenceUtils by lazy { PreferenceUtils(this) }

    private lateinit var deviceInteractionListener: DeviceInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        configureTabLayout()
        configureViewPager()

        if (preferenceUtils.shouldShowTip() == true)
            showTip()
    }

    override fun onBackPressed() {
        if (deviceInteractionListener.onBackPressed())
            super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        when (newConfig.orientation) {
            ORIENTATION_PORTRAIT -> deviceInteractionListener.onScreenOrientationChangedToPortrait()
            ORIENTATION_LANDSCAPE -> deviceInteractionListener.onScreenOrientationChangedToLandscape()
            else -> deviceInteractionListener.onScreenOrientationChangedToPortrait()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.run {
            (findItem(R.id.item_search)?.actionView as SearchView).run {
                //setOnQueryTextListener(QueryListener(adapter, audios)) TODO
            }
        }

        return true
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
            addTab(newTab().setText(R.string.audios))
            addTab(newTab().setText(R.string.videos))
            tabGravity = TabLayout.GRAVITY_FILL
        }
    }

    private fun configureViewPager() {
        val pagerAdapter = PagerAdapter(supportFragmentManager, tab_layout.tabCount)
        deviceInteractionListener = pagerAdapter.getItem(0) as DeviceInteractionListener
        view_pager.run {
            adapter = pagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
            tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    view_pager.currentItem = tab.position
                    deviceInteractionListener = pagerAdapter.getItem(tab.position) as DeviceInteractionListener
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