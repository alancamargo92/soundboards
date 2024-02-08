package com.ukdev.carcadasalborghetti.ui

import android.os.Bundle
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ActivityBaseBinding
import com.ukdev.carcadasalborghetti.databinding.ActivityMainBinding
import com.ukdev.carcadasalborghetti.ui.ads.AdLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override val baseBinding: ActivityBaseBinding
        get() = binding.base

    @Inject
    lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adLoader.loadBannerAds(binding.bannerView, R.string.ads_banner_main)
    }
}
