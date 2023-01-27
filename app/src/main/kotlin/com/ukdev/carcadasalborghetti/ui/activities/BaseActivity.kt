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
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragment
import com.ukdev.carcadasalborghetti.ui.media.MediaHandler
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {

    private var _binding: ActivityBaseBinding? = null
    private val binding: ActivityBaseBinding
        get() = _binding!!

    private val menuProvider by inject<MenuProvider>()
    private val preferencesHelper by inject<PreferencesHelper>()

    private lateinit var mediaHandler: MediaHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
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
        val pagerAdapter = PagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)
        (pagerAdapter.getItem(0) as? MediaListFragment)?.let {
            mediaHandler = it.mediaHandler
        }

        with(binding.viewPager) {
            adapter = pagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
            binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.viewPager.currentItem = tab.position
                    val currentFragment = pagerAdapter.getItem(tab.position)
                    mediaHandler.stop()
                    (currentFragment as? MediaListFragment)?.let {
                        mediaHandler = it.mediaHandler
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