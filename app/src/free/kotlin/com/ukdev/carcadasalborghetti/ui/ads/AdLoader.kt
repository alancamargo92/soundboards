package com.ukdev.carcadasalborghetti.ui.ads

import android.view.View
import androidx.annotation.StringRes

interface AdLoader {

    fun loadBannerAds(target: View, @StringRes adId: Int)
}