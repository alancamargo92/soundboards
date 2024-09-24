package com.ukdev.carcadasalborghetti.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.databinding.ActivityHomeBinding
import com.ukdev.carcadasalborghetti.databinding.ActivityHomeFreeBinding
import com.ukdev.carcadasalborghetti.ui.ads.AdLoader
import com.ukdev.carcadasalborghetti.ui.view.HomeScreen
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
        setUpUi()
    }

    private fun setUpUi() {
        setContent {
            HomeScreen(
                fragmentManager = supportFragmentManager,
                fragments = fragmentListProvider.provideFragmentList(),
                adView = {
                    AndroidView(
                        modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally)
                        ,
                        factory = ::AdView,
                        update = { adView ->
                            adView.setAdSize(AdSize.BANNER)
                            adView.adUnitId = getString(R.string.ads_banner_main)
                            adLoader.loadBannerAds(adView)
                        }
                    )
                }
            )
        }
    }
}
