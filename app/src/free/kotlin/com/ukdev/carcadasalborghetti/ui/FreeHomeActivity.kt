package com.ukdev.carcadasalborghetti.ui

import android.os.Bundle
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ActivityHomeBinding
import com.ukdev.carcadasalborghetti.databinding.ActivityHomeFreeBinding
import com.ukdev.carcadasalborghetti.ui.ads.AdLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FreeHomeActivity : HomeActivity() {

    private var _binding: ActivityHomeFreeBinding? = null
    private val binding: ActivityHomeFreeBinding
        get() = _binding!!

    override val baseBinding: ActivityHomeBinding
        get() = binding.base

    @Inject
    lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityHomeFreeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adLoader.loadBannerAds(binding.bannerView, R.string.ads_banner_main)
    }
}
