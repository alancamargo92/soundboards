package com.ukdev.carcadasalborghetti.ui.ads

import android.view.View
import com.smaato.sdk.banner.ad.BannerAdSize
import com.smaato.sdk.banner.widget.BannerView

class AdLoaderImpl : AdLoader {

    override fun loadBannerAds(target: View, adId: Int) {
        with(target as BannerView) {
            loadAd(context.getString(adId), BannerAdSize.XX_LARGE_320x50)
        }
    }

    override fun destroyBannerAds(target: View) {
        (target as BannerView).destroy()
    }
}