package com.ukdev.carcadasalborghetti.ui.activities

import android.os.Bundle
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.ads.AdLoader
import kotlinx.android.synthetic.free.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val adLoader by inject<AdLoader>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adLoader.loadBannerAds(bannerView, R.string.ads_banner_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        adLoader.destroyBannerAds(bannerView)
    }

}