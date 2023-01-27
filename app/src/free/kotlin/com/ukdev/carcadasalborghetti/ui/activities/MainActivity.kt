package com.ukdev.carcadasalborghetti.ui.activities

import android.os.Bundle
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ActivityMainBinding
import com.ukdev.carcadasalborghetti.ui.ads.AdLoader
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val adLoader by inject<AdLoader>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        adLoader.loadBannerAds(binding.bannerView, R.string.ads_banner_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        adLoader.destroyBannerAds(binding.bannerView)
    }

}