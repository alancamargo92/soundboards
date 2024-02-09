package com.ukdev.carcadasalborghetti.ui

import android.os.Bundle
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ActivityMainBinding
import com.ukdev.carcadasalborghetti.databinding.ActivityMainFreeBinding
import com.ukdev.carcadasalborghetti.ui.ads.AdLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FreeMainActivity : MainActivity() {

    private var _binding: ActivityMainFreeBinding? = null
    private val binding: ActivityMainFreeBinding
        get() = _binding!!

    override val baseBinding: ActivityMainBinding
        get() = binding.base

    @Inject
    lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityMainFreeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adLoader.loadBannerAds(binding.bannerView, R.string.ads_banner_main)
    }
}
