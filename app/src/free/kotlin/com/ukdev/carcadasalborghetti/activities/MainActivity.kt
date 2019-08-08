package com.ukdev.carcadasalborghetti.activities

import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.free.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ad_view.loadAd(AdRequest.Builder().build())
    }

}